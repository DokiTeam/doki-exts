package org.dokiteam.doki.parsers.site.all

import okhttp3.Headers
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.protobuf.ByteString
import com.google.protobuf.CodedInputStream
import com.google.protobuf.UnknownFieldSet     
import com.google.protobuf.WireFormat
import jp.co.comic.jump.proto.MangaplusApi
import org.json.JSONObject
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.core.SinglePageMangaParser
import org.dokiteam.doki.parsers.model.*
import org.dokiteam.doki.parsers.util.*
import org.dokiteam.doki.parsers.util.json.asTypedList
import org.dokiteam.doki.parsers.util.json.getStringOrNull
import org.dokiteam.doki.parsers.util.json.mapJSON
import org.dokiteam.doki.parsers.util.json.mapJSONNotNull
import org.dokiteam.doki.parsers.util.suspendlazy.suspendLazy
import java.util.*

internal abstract class MangaPlusParser(
	context: MangaLoaderContext,
	source: MangaParserSource,
	private val sourceLang: String,
) : SinglePageMangaParser(context, source), Interceptor {

	private val apiUrl = "https://jumpg-webapi.tokyo-cdn.com/api"
	private val appApiUrl = "https://jumpg-api.tokyo-cdn.com/api"
	private val appVersion = "235"
	private val os = "android"
	private val osVersion = "29"
	override val configKeyDomain = ConfigKey.Domain("mangaplus.shueisha.co.jp")

	override fun onCreateConfig(keys: MutableCollection<ConfigKey<*>>) {
		super.onCreateConfig(keys)
		keys.add(userAgentKey)
	}

	override val availableSortOrders: Set<SortOrder> = EnumSet.of(
		SortOrder.POPULARITY,
		SortOrder.UPDATED,
		SortOrder.ALPHABETICAL,
	)

	override val filterCapabilities: MangaListFilterCapabilities
		get() = MangaListFilterCapabilities(
			isSearchSupported = true,
		)

	override suspend fun getFilterOptions() = MangaListFilterOptions()

	private val extraHeaders = Headers.headersOf("Session-Token", UUID.randomUUID().toString())
	private val appHeaders = Headers.headersOf(
		"User-Agent", "okhttp/4.12.0",
		"Connection", "Keep-Alive",
		"Host", "jumpg-api.tokyo-cdn.com",
	)
	private val appSecret = suspendLazy { registerAppDevice() }

	override suspend fun getList(order: SortOrder, filter: MangaListFilter): List<Manga> {
		return when {
			filter.query.isNullOrEmpty() -> {
				when (order) {
					SortOrder.POPULARITY -> getPopularList()
					SortOrder.UPDATED -> getLatestList()
					else -> getAllTitleList()
				}
			}

			else -> getAllTitleList(filter.query)
		}
	}

	private suspend fun getPopularList(): List<Manga> {
		val json = apiCall("/title_list/ranking")

		return json.getJSONObject("titleRankingView")
			.getJSONArray("titles")
			.asTypedList<JSONObject>()
			.toMangaList()
	}

	private suspend fun getLatestList(): List<Manga> {
		val json = apiCall("/title_list/updated")

		return json.getJSONObject("titleUpdatedView")
			.getJSONArray("latestTitle")
			.mapJSON { it.getJSONObject("title") }
			.toMangaList()
	}

	// since search is local, save network calls on related manga call
	private val allTitleCache = suspendLazy {
		apiCall("/title_list/allV2")
			.getJSONObject("allTitlesViewV2")
			.getJSONArray("AllTitlesGroup")
			.mapJSON { it.getJSONArray("titles").asTypedList<JSONObject>() }
			.flatten()
	}

	private suspend fun getAllTitleList(query: String? = null): List<Manga> {
		return allTitleCache.get().toMangaList(query)
	}

	private fun List<JSONObject>.toMangaList(query: String? = null): List<Manga> {
		return mapNotNull {
			val language = it.getStringOrNull("language") ?: "ENGLISH"

			if (language != sourceLang) {
				return@mapNotNull null
			}

			val name = it.getString("name")
			val author = it.getString("author")
				.split('/')
				.joinToString(transform = String::trim)

			// filter out any other title or author which doesn't match search input
			if (query != null && !(name.contains(query, true) || author.contains(query, true))) {
				return@mapNotNull null
			}

			val titleId = it.getInt("titleId").toString()

			Manga(
				id = generateUid(titleId),
				url = titleId,
				publicUrl = "/titles/$titleId".toAbsoluteUrl(domain),
				title = name,
				coverUrl = it.getString("portraitImageUrl"),
				altTitles = emptySet(),
				authors = setOf(author),
				contentRating = null,
				rating = RATING_UNKNOWN,
				state = null,
				source = source,
				tags = emptySet(),
			)
		}
	}

	override suspend fun getDetails(manga: Manga): Manga {
		val json = apiCall("/title_detailV3?title_id=${manga.url}")
			.getJSONObject("titleDetailView")
		val title = json.getJSONObject("title")

		val completed = json.getJSONObject("titleLabels")
			.getString("releaseSchedule").let {
				it == "DISABLED" || it == "COMPLETED"
			}

		val hiatus = json.getStringOrNull("nonAppearanceInfo")?.contains("on a hiatus") == true
		val author = title.getString("author")
			.split("/").joinToString(transform = String::trim)
		val chapters = runCatching {
			parseAppChapters(title.getInt("titleId"))
		}.getOrElse {
			parseChapters(
				json,
				title.getStringOrNull("language") ?: "ENGLISH",
			)
		}

		return manga.copy(
			title = title.getString("name"),
			publicUrl = "/titles/${title.getInt("titleId")}".toAbsoluteUrl(domain),
			coverUrl = title.getString("portraitImageUrl"),
			authors = setOf(author),
			description = buildString {
				json.getString("overview").let(::append)
				json.getStringOrNull("viewingPeriodDescription")
					?.takeIf { !completed }
					?.let { append("<br><br>", it) }
			},
			chapters = chapters,
			state = when {
				completed -> MangaState.FINISHED
				hiatus -> MangaState.PAUSED
				else -> MangaState.ONGOING
			},
		)
	}

	private fun parseChapters(titleDetailView: JSONObject, language: String): List<MangaChapter> {
		val chapterListFromGroups = titleDetailView
			.optJSONArray("chapterListGroup")
			?.asTypedList<JSONObject>()
			?.flatMap {
				it.optJSONArray("firstChapterList")?.asTypedList<JSONObject>().orEmpty() +
					it.optJSONArray("midChapterList")?.asTypedList<JSONObject>().orEmpty() +
					it.optJSONArray("lastChapterList")?.asTypedList<JSONObject>().orEmpty()
			}
			.orEmpty()
		val chapterListFallback = titleDetailView.optJSONArray("firstChapterList")
			?.asTypedList<JSONObject>()
			.orEmpty() + titleDetailView.optJSONArray("lastChapterList")
			?.asTypedList<JSONObject>()
			.orEmpty()
		val chapterList = chapterListFromGroups + chapterListFallback

		return chapterList.mapChapters { _, chapter ->
			val chapterId = chapter.getInt("chapterId").toString()
			val subtitle = chapter.getStringOrNull("subTitle") ?: return@mapChapters null

			MangaChapter(
				id = generateUid(chapterId),
				url = chapterId,
				title = subtitle,
				number = chapter.getString("name")
					.substringAfter("#")
					.toFloatOrNull() ?: -1f,
				volume = 0,
				uploadDate = chapter.getInt("startTimeStamp") * 1000L,
				branch = when (language) {
					"PORTUGUESE_BR" -> "Portuguese (Brazil)"
					else -> language.lowercase().toTitleCase()
				},
				scanlator = null,
				source = source,
			)
		}
	}

	private suspend fun parseAppChapters(titleId: Int): List<MangaChapter> {
		val responseBytes = appApiCallBytes(
			"/title_detailV3?title_id=$titleId&lang=${contentLangCode(sourceLang)}&clang=${contentLangCode(sourceLang)}",
		)
		val chapterList = findAppChapterEntries(UnknownFieldSet.parseFrom(responseBytes))
			.distinctBy { it.chapterId }

		check(chapterList.isNotEmpty()) { "No chapters found in app response" }

		return chapterList.mapChapters { _, chapter ->
			val subtitle = chapter.subTitle.takeIf { it.isNotBlank() } ?: return@mapChapters null
			val chapterId = chapter.chapterId.toString()
			MangaChapter(
				id = generateUid(chapterId),
				url = chapterId,
				title = subtitle,
				number = chapter.name.substringAfter("#").toFloatOrNull() ?: -1f,
				volume = 0,
				uploadDate = chapter.startTimeStamp * 1000L,
				branch = when (sourceLang) {
					"PORTUGUESE_BR" -> "Portuguese (Brazil)"
					else -> sourceLang.lowercase().toTitleCase()
				},
				scanlator = null,
				source = source,
			)
		}
	}

	private data class AppChapterEntry(
		val chapterId: Int,
		val name: String,
		val subTitle: String,
		val startTimeStamp: Long,
	)

	private fun parseAppChapterEntry(group: UnknownFieldSet): AppChapterEntry? {
		fun varint(number: Int): Long = group.asMap()[number]?.varintList?.firstOrNull() ?: 0L
		fun str(number: Int): String = group.asMap()[number]?.lengthDelimitedList?.firstOrNull()?.toUtf8().orEmpty()
		val chapterId = varint(2).toInt()
		val name = str(3)
		val subTitle = str(4)
		val startTimeStamp = varint(6)
		if (chapterId == 0 || subTitle.isBlank()) {
			return null
		}
		return AppChapterEntry(
			chapterId = chapterId,
			name = name,
			subTitle = subTitle,
			startTimeStamp = startTimeStamp,
		)
	}

	private fun findAppChapterEntries(root: UnknownFieldSet): List<AppChapterEntry> {
		val result = mutableListOf<AppChapterEntry>()

		fun walk(set: UnknownFieldSet) {
			for ((fieldNumber, field) in set.asMap()) {
				if (fieldNumber == 38) {
					field.groupList.mapNotNullTo(result, ::parseAppChapterEntry)
					field.lengthDelimitedList
						.mapNotNull { bytes -> runCatching { parseAppChapterEntry(bytes.toByteArray()) }.getOrNull() }
						.mapNotNullTo(result) { it }
				}
				field.groupList.forEach(::walk)
				field.lengthDelimitedList.forEach { bytes ->
					runCatching { UnknownFieldSet.parseFrom(bytes) }
						.onSuccess(::walk)
				}
			}
		}

		walk(root)
		return result
	}

	private fun parseAppChapterEntry(bytes: ByteArray): AppChapterEntry? {
		var chapterId = 0
		var name = ""
		var subTitle = ""
		var startTimeStamp = 0L
		val input = CodedInputStream.newInstance(bytes)

		while (!input.isAtEnd) {
			val tag = input.readTag()
			if (tag == 0) break
			when (WireFormat.getTagFieldNumber(tag)) {
				2 -> chapterId = input.readInt32()
				3 -> name = input.readStringRequireUtf8()
				4 -> subTitle = input.readStringRequireUtf8()
				6 -> startTimeStamp = input.readInt64()
				else -> input.skipField(tag)
			}
		}
		if (chapterId == 0 || subTitle.isBlank()) {
			return null
		}
		return AppChapterEntry(
			chapterId = chapterId,
			name = name,
			subTitle = subTitle,
			startTimeStamp = startTimeStamp,
		)
	}

	private fun ByteString.toUtf8(): String = toStringUtf8()

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val pages = runCatching {
			apiCall("/manga_viewer?chapter_id=${chapter.url}&split=yes&img_quality=super_high")
		}.getOrElse { primaryError ->
			// Fallback to app-like flags used by MangaPlus API clients when default viewer call is denied.
			val fallbackQueries = arrayOf(
				"/manga_viewer?chapter_id=${chapter.url}&split=yes&img_quality=super_high&free_reading=yes&viewer_mode=vertical&clang=${contentLangCode(sourceLang)}",
				"/manga_viewer?chapter_id=${chapter.url}&split=yes&img_quality=super_high&subscription_reading=yes&viewer_mode=vertical&clang=${contentLangCode(sourceLang)}",
				"/manga_viewer?chapter_id=${chapter.url}&split=yes&img_quality=super_high&ticket_reading=yes&viewer_mode=vertical&clang=${contentLangCode(sourceLang)}",
			)
			var lastError = primaryError
			for (query in fallbackQueries) {
				val result = runCatching { apiCall(query) }
				result.onSuccess { return@getOrElse it }
				lastError = result.exceptionOrNull() ?: lastError
			}
			runCatching { return@getOrElse appViewerApiCall(chapter.url.toInt()) }
			throw lastError
		}.getJSONObject("mangaViewer").getJSONArray("pages")

		return pages.mapJSONNotNull {
			val mangaPage = it.optJSONObject("mangaPage")
				?: return@mapJSONNotNull null
			val url = mangaPage.getString("imageUrl")
			val encryptionKey = mangaPage.getStringOrNull("encryptionKey")
			MangaPage(
				id = generateUid(url),
				url = url + if (encryptionKey == null) "" else "#$encryptionKey",
				preview = null,
				source = source,
			)
		}
	}

	// image descrambling
	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
		val response = chain.proceed(request)
		val encryptionKey = request.url.fragment

		if (encryptionKey.isNullOrEmpty()) {
			return response
		}

		return response.map { responseBody ->
			val contentType = response.headers["Content-Type"] ?: "image/jpeg"
			val image = responseBody.bytes().decodeXorCipher(encryptionKey)
			image.toResponseBody(contentType.toMediaTypeOrNull())
		}
	}

	private fun ByteArray.decodeXorCipher(key: String): ByteArray {
		val keyStream = key.chunked(2)
			.map { it.toInt(16) }

		return mapIndexed { i, byte -> byte.toInt() xor keyStream[i % keyStream.size] }
			.map(Int::toByte)
			.toByteArray()
	}

	private suspend fun appViewerApiCall(chapterId: Int): JSONObject {
		val success = appApiCall(
			"/manga_viewer?chapter_id=$chapterId&split=yes&img_quality=super_high&viewer_mode=vertical&clang=${contentLangCode(sourceLang)}",
		)
		val pages = success.mangaViewer.pagesList
		return JSONObject().put(
			"mangaViewer",
			JSONObject().put(
				"pages",
				org.json.JSONArray(
					pages.mapNotNull { page ->
						if (!page.hasMangaPage()) {
							return@mapNotNull null
						}
						val mangaPage = page.mangaPage
						JSONObject().put(
							"mangaPage",
							JSONObject()
								.put("imageUrl", mangaPage.imageUrl)
								.put("encryptionKey", mangaPage.encryptionKey),
						)
					},
				),
			),
		)
	}

	private suspend fun registerAppDevice(): String {
		val deviceToken = UUID.randomUUID().toString().md5()
		val securityKey = (deviceToken + "4Kin9vGg").md5()
		val success = appApiCall(
			"/register?device_token=$deviceToken&security_key=$securityKey",
			method = "PUT",
			withSecret = false,
		)
		val secret = success.registerationData.deviceSecret
		check(secret.isNotBlank()) { "Cannot obtain app secret" }
		return secret
	}

	private suspend fun appApiCall(
		url: String,
		method: String = "GET",
		withSecret: Boolean = true,
	): MangaplusApi.SuccessResult {
		val response = MangaplusApi.Response.parseFrom(appApiCallBytes(url, method, withSecret))

		return if (response.hasSuccess()) {
			response.success
		} else {
			val error = response.error
			val message = error.englishPopup.body.takeIf { it.isNotBlank() }
				?: error.debugInfo.takeIf { it.isNotBlank() }
				?: "Unknown Error"
			error(message)
		}
	}

	private suspend fun appApiCallBytes(
		url: String,
		method: String = "GET",
		withSecret: Boolean = true,
	): ByteArray {
		val newUrl = "$appApiUrl$url".toHttpUrl().newBuilder()
			.addQueryParameter("os", os)
			.addQueryParameter("os_ver", osVersion)
			.addQueryParameter("app_ver", appVersion)
			.apply {
				if (withSecret) {
					addQueryParameter("secret", appSecret.get())
				}
			}
			.build()
		val requestBuilder = Request.Builder()
			.url(newUrl)
			.headers(appHeaders)
		if (method == "PUT") {
			requestBuilder.put("".toRequestBody())
		} else {
			requestBuilder.get()
		}

		return context.httpClient.newCall(requestBuilder.build()).await().use { it.body.bytes() }
	}

	private suspend fun apiCall(url: String): JSONObject {
		val newUrl = "$apiUrl$url".toHttpUrl().newBuilder()
			.addQueryParameter("format", "json")
			.addQueryParameter("os", os)
			.addQueryParameter("os_ver", osVersion)
			.addQueryParameter("app_ver", appVersion)
			.build()
		val response = webClient.httpGet(newUrl, extraHeaders).parseJson()

		val success = response.optJSONObject("success")

		return checkNotNull(success) {
			val error = response.getJSONObject("error")
			val reason = error.getJSONArray("popups")
				.asTypedList<JSONObject>()
				.firstOrNull { it.getStringOrNull("language") == null }

			if (reason?.getStringOrNull("subject") == "Not Found" && url.contains("manga_viewer")) {
				"This chapter has expired"
			} else {
				reason?.getStringOrNull("body") ?: "Unknown Error"
			}
		}
	}

	private fun contentLangCode(language: String): String = when (language) {
		"ENGLISH" -> "eng"
		"SPANISH" -> "esp"
		"FRENCH" -> "fra"
		"INDONESIAN" -> "ind"
		"PORTUGUESE_BR" -> "ptb"
		"RUSSIAN" -> "rus"
		"THAI" -> "tha"
		"GERMAN" -> "deu"
		"VIETNAMESE" -> "vie"
		else -> "eng"
	}

	@MangaSourceParser("MANGAPLUSPARSER_EN", "MANGA Plus English", "en")
	class English(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_EN,
		"ENGLISH",
	)

	@MangaSourceParser("MANGAPLUSPARSER_ES", "MANGA Plus Spanish", "es")
	class Spanish(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_ES,
		"SPANISH",
	)

	@MangaSourceParser("MANGAPLUSPARSER_FR", "MANGA Plus French", "fr")
	class French(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_FR,
		"FRENCH",
	)

	@MangaSourceParser("MANGAPLUSPARSER_ID", "MANGA Plus Indonesian", "id")
	class Indonesian(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_ID,
		"INDONESIAN",
	)

	@MangaSourceParser("MANGAPLUSPARSER_PTBR", "MANGA Plus Portuguese (Brazil)", "pt")
	class Portuguese(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_PTBR,
		"PORTUGUESE_BR",
	)

	@MangaSourceParser("MANGAPLUSPARSER_RU", "MANGA Plus Russian", "ru")
	class Russian(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_RU,
		"RUSSIAN",
	)

	@MangaSourceParser("MANGAPLUSPARSER_TH", "MANGA Plus Thai", "th")
	class Thai(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_TH,
		"THAI",
	)

	@MangaSourceParser("MANGAPLUSPARSER_VI", "MANGA Plus Vietnamese", "vi")
	class Vietnamese(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_VI,
		"VIETNAMESE",
	)

	@MangaSourceParser("MANGAPLUSPARSER_DE", "MANGA Plus German", "de")
	class German(context: MangaLoaderContext) : MangaPlusParser(
		context,
		MangaParserSource.MANGAPLUSPARSER_DE,
		"GERMAN",
	)
}
