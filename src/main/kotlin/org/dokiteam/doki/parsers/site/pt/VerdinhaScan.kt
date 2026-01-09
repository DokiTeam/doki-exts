package org.dokiteam.doki.parsers.site.pt

import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONObject
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.core.PagedMangaParser
import org.dokiteam.doki.parsers.model.ContentRating
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.Manga
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaListFilterOptions
import org.dokiteam.doki.parsers.model.MangaPage
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.MangaState
import org.dokiteam.doki.parsers.model.MangaTag
import org.dokiteam.doki.parsers.model.RATING_UNKNOWN
import org.dokiteam.doki.parsers.model.SortOrder
import org.dokiteam.doki.parsers.util.generateUid
import org.dokiteam.doki.parsers.util.json.mapJSON
import org.dokiteam.doki.parsers.util.json.mapJSONNotNull
import org.dokiteam.doki.parsers.util.oneOrThrowIfMany
import org.dokiteam.doki.parsers.util.parseJson
import org.dokiteam.doki.parsers.util.parseSafe
import org.dokiteam.doki.parsers.util.toTitleCase
import java.text.SimpleDateFormat
import java.util.EnumSet

@MangaSourceParser("VERDINHA", "Verdinha", "pt")
internal class VerdinhaScan(context: MangaLoaderContext) : PagedMangaParser(
	context,
	source = MangaParserSource.VERDINHA,
	pageSize = 24,
	searchPageSize = 15,
) {
	override val configKeyDomain = ConfigKey.Domain("verdinha.wtf")
	private val apiUrl = "https://api.verdinha.wtf"
	private val cdnUrl = "https://cdn.verdinha.wtf"
	private val scanId = 1

	override val availableSortOrders: Set<SortOrder> = EnumSet.of(
		SortOrder.UPDATED,
		SortOrder.POPULARITY,
		SortOrder.POPULARITY_TODAY,
		SortOrder.POPULARITY_WEEK,
		SortOrder.POPULARITY_MONTH,
	)

	override val filterCapabilities: MangaListFilterCapabilities
		get() = MangaListFilterCapabilities(
			isSearchSupported = true,
			isSearchWithFiltersSupported = true,
			isMultipleTagsSupported = true,
		)

	override suspend fun getFilterOptions(): MangaListFilterOptions {
		return MangaListFilterOptions(
			availableTags = fetchAvailableTags(),
			availableStates = EnumSet.of(
				MangaState.ONGOING,
				MangaState.FINISHED,
				MangaState.PAUSED,
				MangaState.ABANDONED,
			),
			availableContentTypes = EnumSet.of(
				ContentType.MANGA,
				ContentType.MANHUA,
				ContentType.MANHWA,
				ContentType.HENTAI,
			),
		)
	}

	private val apiHeaders: Headers
		get() = Headers.Builder()
			.add("Referer", "https://$domain/")
			.add("scan-id", scanId.toString())
			.build()

	private val chapterDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", sourceLocale)

	override suspend fun getListPage(page: Int, order: SortOrder, filter: MangaListFilter): List<Manga> {
		val genId = when {
			filter.types.oneOrThrowIfMany() == ContentType.HENTAI -> "5"
			else -> "1"
		}

		val url = when {
			!filter.query.isNullOrEmpty() || filter.tags.isNotEmpty() ||
				filter.states.isNotEmpty() -> buildSearchUrl(page, filter)

			// Popularity rankings
			order in setOf(
				SortOrder.POPULARITY,
				SortOrder.POPULARITY_TODAY,
				SortOrder.POPULARITY_WEEK,
				SortOrder.POPULARITY_MONTH,
			) -> {
				val period = when (order) {
					SortOrder.POPULARITY_TODAY -> "dia"
					SortOrder.POPULARITY_WEEK -> "semana"
					SortOrder.POPULARITY_MONTH -> "mes"
					else -> "geral" // all time
				}
				"$apiUrl/obras/ranking".toHttpUrl().newBuilder()
					.addQueryParameter("periodo", period)
					.addQueryParameter("limite", pageSize.toString())
					.addQueryParameter("pagina", page.toString())
					.addQueryParameter("gen_id", genId)
					.build()
			}
			// Default to updated
			else -> {
				"$apiUrl/obras/novos-capitulos".toHttpUrl().newBuilder()
					.addQueryParameter("limite", pageSize.toString())
					.addQueryParameter("pagina", page.toString())
					.addQueryParameter("gen_id", genId)
					.build()
			}
		}

		val response = webClient.httpGet(url, apiHeaders).parseJson()
		val results = response.optJSONArray("resultados") ?: return emptyList()
		return results.mapJSON { parseMangaFromJson(it) }
	}

	private fun buildSearchUrl(page: Int, filter: MangaListFilter): HttpUrl {
		val builder = "$apiUrl/obras".toHttpUrl().newBuilder()
			.addQueryParameter("obr_nome", filter.query ?: "")
			.addQueryParameter("limite", "15")
			.addQueryParameter("pagina", page.toString())

		val isHentai = filter.types.firstOrNull() == ContentType.HENTAI

		if (isHentai) builder.addQueryParameter("gen_id", "5") else builder.addQueryParameter("todos_generos", "true")

		// Add tags
		filter.tags.forEach { tag ->
			builder.addQueryParameter("tags[]", tag.key)
		}

		// Add format (content type)
		filter.types.oneOrThrowIfMany().let { contentType ->
			val type = when (contentType) {
				ContentType.MANHWA -> "1"
				ContentType.MANHUA -> "2"
				ContentType.MANGA -> "3"
				else -> null
			}
			type?.let { builder.addQueryParameter("formt_id", it) }
		}

		// Add status
		filter.states.firstOrNull()?.let { state ->
			val statusId = when (state) {
				MangaState.ONGOING -> "1"
				MangaState.FINISHED -> "2"
				MangaState.PAUSED -> "3"
				MangaState.ABANDONED -> "4"
				else -> null
			}
			statusId?.let { builder.addQueryParameter("stt_id", it) }
		}

		return builder.build()
	}

	private fun parseMangaFromJson(json: JSONObject): Manga {
		val id = json.getInt("obr_id")
		val name = json.getString("obr_nome")
		val slug = json.optString("obr_slug", "").ifEmpty {
			name.lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')
		}
		val coverPath = json.optString("obr_imagem", "")

		val coverUrl = when {
			coverPath.startsWith("http") -> coverPath
			coverPath.startsWith("wp-content") -> "$cdnUrl/$coverPath"
			coverPath.isNotEmpty() -> "$cdnUrl/scans/$scanId/obras/$id/$coverPath"
			else -> ""
		}

		val isNsfw = json.optBoolean("obr_mais_18", false)
		val rating = json.optString("rating").toFloatOrNull()?.div(5f) ?: RATING_UNKNOWN

		return Manga(
			id = generateUid(id.toLong()),
			title = name,
			url = "/obra/$id/$slug",
			publicUrl = "https://$domain/obra/$id/$slug",
			coverUrl = coverUrl,
			source = source,
			rating = rating,
			altTitles = emptySet(),
			contentRating = if (isNsfw) ContentRating.ADULT else ContentRating.SAFE,
			tags = emptySet(),
			state = null,
			authors = emptySet(),
			largeCoverUrl = null,
			description = null,
			chapters = null,
		)
	}

	override suspend fun getDetails(manga: Manga): Manga {
		val mangaId = manga.url.substringAfter("/obra/").substringBefore("/")
		val response = webClient.httpGet("$apiUrl/obras/$mangaId", apiHeaders).parseJson()

		val description = response.optString("obr_descricao")
			.replace(Regex("</?strong>"), "")
			.replace("\\/", "/")
			.replace(Regex("&lt;"), "<")
			.replace(Regex("&gt;"), ">")
			.replace(Regex("\\s+"), " ")
			.trim()

		val status = response.optJSONObject("status")
			?.optString("stt_nome")
			?.let { parseStatus(it) }

		val tags = response.optJSONArray("tags")?.mapJSON { tagJson ->
			val tagName = tagJson.getString("tag_nome")
			MangaTag(
				key = tagJson.optInt("tag_id").toString(),
				title = tagName.toTitleCase(),
				source = source,
			)
		}?.toSet() ?: emptySet()

		val chapters = response.optJSONArray("capitulos")?.mapJSON { chapterJson ->
			parseChapter(chapterJson)
		}?.asReversed() ?: emptyList()

		return manga.copy(
			title = response.optString("obr_nome", manga.title),
			description = description,
			state = status,
			tags = tags,
			chapters = chapters,
		)
	}

	private fun parseChapter(json: JSONObject): MangaChapter {
		val chapterId = json.getInt("cap_id")
		val chapterName = json.getString("cap_nome")
		val chapterDate = json.optString("cap_criado_em").ifEmpty {
			json.optString("cap_liberar_em")
		}

		val chapterNumber = json.optDouble("cap_numero").let {
			if (it > 0) it.toFloat() else {
				chapterName
					.substringAfter("Capítulo ", "")
					.substringBefore(" ")
					.replace(",", ".")
					.toFloatOrNull() ?: 0f
			}
		}

		return MangaChapter(
			id = generateUid(chapterId.toLong()),
			title = chapterName,
			number = chapterNumber,
			url = "/capitulo/$chapterId",
			uploadDate = chapterDateFormat.parseSafe(chapterDate),
			source = source,
			volume = 0,
			scanlator = null,
			branch = null,
		)
	}

	private fun parseStatus(status: String): MangaState? = when (status.lowercase()) {
		"em andamento" -> MangaState.ONGOING
		"completo" -> MangaState.FINISHED
		"hiato" -> MangaState.PAUSED
		"cancelado" -> MangaState.ABANDONED
		else -> null
	}

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val chapterId = chapter.url.substringAfter("/capitulo/")

		val response = webClient.httpGet("$apiUrl/capitulos/$chapterId", apiHeaders).parseJson()

		// Parse pages from the response
		val pagesArray = response.optJSONArray("cap_paginas")
			?: throw Exception("No pages found in chapter")

		val mangaId = response.optJSONObject("obra")?.optInt("obr_id")
			?: response.optInt("obr_id")

		val chapterNumber = response.optDouble("cap_numero").let { num ->
			when {
				num > 0 -> {
					if (num % 1 == 0.0) num.toInt().toString() else num.toString().replace(".", "_")
				}
				else -> {
					response.optString("cap_nome", "")
						.substringAfter("Capítulo ", "")
						.substringBefore(" ")
						.replace(",", ".")
						.replace(".", "_")
						.ifEmpty { "0" }
				}
			}
		}

		return pagesArray.mapJSONNotNull { pageJson ->
			// Try to get path first (new format), then src (old format)
			val pagePath = pageJson.optString("path").ifEmpty {
				pageJson.optString("src")
			}

			if (pagePath.isEmpty()) return@mapJSONNotNull null

			val imageUrl = when {
				// Already a full URL
				pagePath.startsWith("http") -> pagePath
				// Direct path format: "scans/1/obras/1053/capitulos/1/001.jpg"
				pagePath.startsWith("scans/") -> "$cdnUrl/$pagePath"
				// WordPress manga path: "manga_.../hash/001.webp"
				pagePath.startsWith("manga_") -> "$cdnUrl/wp-content/uploads/WP-manga/data/$pagePath"
				// WordPress legacy path: "wp-content/uploads/..."
				pagePath.startsWith("wp-content") -> "$cdnUrl/$pagePath"
				// Simple filename (like "001.webp")
				else -> {
					val safeChapterNumber = chapterNumber.replace(".", "_")
					"$cdnUrl/scans/$scanId/obras/$mangaId/capitulos/$safeChapterNumber/$pagePath"
				}
			}

			MangaPage(
				id = generateUid(imageUrl),
				url = imageUrl,
				source = source,
				preview = null,
			)
		}
	}

	private suspend fun fetchAvailableTags(): Set<MangaTag> {
		val url = "$apiUrl/tags".toHttpUrl().newBuilder()
			.build()

		val response = webClient.httpGet(url, apiHeaders).parseJson()
		val tagsArray = response.optJSONArray("resultados")

		if (tagsArray == null) return emptySet()

		return tagsArray.mapJSON { tagJson ->
			MangaTag(
				key = tagJson.getInt("tag_id").toString(),
				title = tagJson.getString("tag_nome").toTitleCase(),
				source = source,
			)
		}.toSet()
	}
}
