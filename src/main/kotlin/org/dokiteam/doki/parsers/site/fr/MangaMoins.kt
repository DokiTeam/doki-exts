package org.dokiteam.doki.parsers.site.fr

import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.core.PagedMangaParser
import org.dokiteam.doki.parsers.exception.ParseException
import org.dokiteam.doki.parsers.model.Manga
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaListFilterOptions
import org.dokiteam.doki.parsers.model.MangaPage
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.MangaSource
import org.dokiteam.doki.parsers.model.MangaState
import org.dokiteam.doki.parsers.model.RATING_UNKNOWN
import org.dokiteam.doki.parsers.model.SortOrder
import org.dokiteam.doki.parsers.util.generateUid
import org.dokiteam.doki.parsers.util.parseHtml
import org.dokiteam.doki.parsers.util.parseJson
import org.dokiteam.doki.parsers.util.toAbsoluteUrl
import org.json.JSONObject
import org.jsoup.nodes.Document
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.EnumSet
import java.util.Locale

@MangaSourceParser("MANGAMOINS", "MangaMoins", "fr")
internal class MangaMoins(context: MangaLoaderContext) :
	PagedMangaParser(context, MangaParserSource.MANGAMOINS, API_LIMIT) {

	override val configKeyDomain = ConfigKey.Domain("mangamoins.com")
	override val availableSortOrders: Set<SortOrder> = EnumSet.of(SortOrder.UPDATED)
	override val filterCapabilities = MangaListFilterCapabilities(isSearchSupported = true)

	override fun getRequestHeaders(): Headers = super.getRequestHeaders().newBuilder()
		.add("Referer", "https://$domain/")
		.build()

	override suspend fun getFilterOptions(): MangaListFilterOptions = MangaListFilterOptions()

	override suspend fun getListPage(page: Int, order: SortOrder, filter: MangaListFilter): List<Manga> {
		val url = HttpUrl.Builder()
			.scheme("https")
			.host(domain)
			.addPathSegments("api/v1/mangas")
			.addQueryParameter("page", page.toString())
			.addQueryParameter("limit", API_LIMIT.toString())
			.apply {
				filter.query?.trim()?.takeIf { it.isNotEmpty() }?.let { q ->
					addQueryParameter("q", q)
				}
			}
			.build()
		val json = webClient.httpGet(url).parseJson()
		if (json.optString("status").equals("error", ignoreCase = true)) {
			throw ParseException(json.optString("message"), url.toString())
		}
		val data = json.optJSONArray("data") ?: return emptyList()
		val list = ArrayList<Manga>(data.length())
		for (i in 0 until data.length()) {
			val jo = data.optJSONObject(i) ?: continue
			val title = jo.optString("title").trim()
			if (title.isEmpty()) continue
			val slug = title.toMangaSlug()
			val coverFolder = jo.optString("cover_folder").trim()
			val coverUrl = coverFolder.takeIf { it.isNotEmpty() }?.let {
				"https://$domain/files/scans/$it/thumbnail.webp"
			}
			list += Manga(
				id = generateUid(slug),
				title = title,
				altTitles = emptySet(),
				url = "/manga/$slug",
				publicUrl = "https://$domain/manga/$slug",
				rating = RATING_UNKNOWN,
				contentRating = null,
				coverUrl = coverUrl,
				tags = emptySet(),
				state = null,
				authors = emptySet(),
				source = source,
			)
		}
		return list
	}

	override suspend fun getDetails(manga: Manga): Manga {
		val json = fetchMangaJson(manga)
		val info = json.optJSONObject("info")
		val detailsTitle = info?.optString("title").orEmpty().ifBlank { manga.title }
		val chapters = parseChapters(json).ifEmpty { manga.chapters.orEmpty() }
		val description = info?.optString("description").orEmpty().trim().ifEmpty { null }
		val authors = parseAuthors(info?.optString("author")).ifEmpty { manga.authors }
		val coverUrl = info?.optString("cover").orEmpty().trim().ifEmpty { null }?.toAbsoluteUrl(domain)
		val canonicalSlug = extractSlugFromMangaUrl(manga.url)
			?: extractSlugFromMangaUrl(manga.publicUrl)
			?: detailsTitle.toMangaSlug()
		return manga.copy(
			title = detailsTitle,
			publicUrl = "https://$domain/manga/$canonicalSlug",
			coverUrl = coverUrl ?: manga.coverUrl,
			description = description ?: manga.description,
			state = parseState(info?.optString("status")) ?: manga.state,
			authors = authors,
			chapters = chapters.takeIf { it.isNotEmpty() } ?: manga.chapters,
		)
	}

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val doc = webClient.httpGet(chapter.url.toAbsoluteUrl(domain)).parseHtml()
		val preloaded = doc.select("link[rel=preload][as=image]")
			.map { it.attr("href").trim() }
			.filter { it.isNotEmpty() }
			.map { it.toAbsoluteUrl(domain) }
			.distinct()
		if (preloaded.isNotEmpty()) {
			return preloaded.map { pageUrl ->
				MangaPage(
					id = generateUid(pageUrl),
					url = pageUrl,
					preview = null,
					source = chapter.source,
				)
			}
		}
		val folder = extractScanFolder(chapter.url) ?: return emptyList()
		return parsePagesFromScript(doc, folder, chapter.source)
	}

	private suspend fun fetchMangaJson(manga: Manga): JSONObject {
		val candidates = buildDetailsCandidates(manga)
		if (candidates.isEmpty()) {
			throw ParseException("Cannot build manga lookup candidates", manga.publicUrl)
		}
		var bestScore = Int.MIN_VALUE
		var bestMatch: JSONObject? = null
		for (title in candidates) {
			val url = HttpUrl.Builder()
				.scheme("https")
				.host(domain)
				.addPathSegments("api/v1/manga")
				.addQueryParameter("manga", title)
				.build()
			val json = webClient.httpGet(url).parseJson()
			val score = scoreDetailsResponse(json)
			if (score > bestScore) {
				bestScore = score
				bestMatch = json
			}
			if (score >= DETAILS_SCORE_WITH_CHAPTERS) {
				return json
			}
		}
		return bestMatch ?: throw ParseException("Cannot load manga details", manga.publicUrl)
	}

	private fun parseChapters(json: JSONObject): List<MangaChapter> {
		val ja = json.optJSONArray("chapters") ?: return emptyList()
		val result = ArrayList<MangaChapter>(ja.length())
		for (i in ja.length() - 1 downTo 0) {
			val jo = ja.optJSONObject(i) ?: continue
			val folder = jo.optString("folder").trim()
			if (folder.isEmpty()) continue
			val chapterTitle = jo.optString("title").trim().ifEmpty { null }
			val chapterNumber = parseChapterNumber(jo.optString("num"), folder)
			result += MangaChapter(
				id = generateUid(folder),
				title = chapterTitle,
				number = chapterNumber,
				volume = 0,
				url = "/scan/$folder",
				scanlator = null,
				uploadDate = jo.optLong("time", 0L) * 1000L,
				branch = null,
				source = source,
			)
		}
		return result.distinctBy { it.url }
	}

	private fun parsePagesFromScript(doc: Document, folder: String, source: MangaSource): List<MangaPage> {
		val scriptData = doc.select("script")
			.firstOrNull { it.data().contains("imageMtimes") }
			?.data()
			.orEmpty()
		val payload = IMAGE_MTIMES_BLOCK.find(scriptData)?.groupValues?.getOrNull(1).orEmpty()
		if (payload.isEmpty()) return emptyList()
		val pairs = IMAGE_MTIMES_ENTRY.findAll(payload)
			.mapNotNull { m ->
				val page = m.groupValues.getOrNull(1)?.toIntOrNull() ?: return@mapNotNull null
				val mtime = m.groupValues.getOrNull(2)?.takeIf { it.isNotEmpty() } ?: return@mapNotNull null
				page to mtime
			}
			.sortedBy { it.first }
			.map { (page, mtime) ->
				val pageName = page.toString().padStart(2, '0')
				"/files/scans/$folder/$pageName.png?v=$mtime"
			}
			.distinct()
		return pairs.map { pageUrl ->
			MangaPage(
				id = generateUid(pageUrl),
				url = pageUrl,
				preview = null,
				source = source,
			)
		}.toList()
	}

	private fun parseState(status: String?): MangaState? {
		val value = status?.lowercase(Locale.ROOT)?.trim() ?: return null
		return when {
			"cours" in value -> MangaState.ONGOING
			"term" in value || "fini" in value -> MangaState.FINISHED
			else -> null
		}
	}

	private fun parseAuthors(raw: String?): Set<String> {
		return raw.orEmpty()
			.split(AUTHORS_SPLIT_PATTERN)
			.map { it.trim() }
			.filter { it.isNotEmpty() && !it.equals("Auteur Inconnu", ignoreCase = true) }
			.toSet()
	}

	private fun parseChapterNumber(raw: String, folder: String): Float {
		val fromLabel = raw.replace("#", "").trim().toFloatOrNull()
		if (fromLabel != null) return fromLabel
		val fromFolder = CHAPTER_NUMBER_REGEX.find(folder)?.groupValues?.getOrNull(1)?.toFloatOrNull()
		return fromFolder ?: 0f
	}

	private fun buildDetailsCandidates(manga: Manga): List<String> {
		val ordered = listOfNotNull(
			extractSlugFromMangaUrl(manga.url)?.toMangaTitle(),
			extractSlugFromMangaUrl(manga.publicUrl)?.toMangaTitle(),
			legacyMangaTitle(manga.url),
			manga.title.trim().takeIf { it.isNotEmpty() },
		).distinctBy { it.lowercase(Locale.ROOT) }

		return when {
			ordered.size <= 1 -> ordered
			else -> ordered.filterNot { it.equals(UNKNOWN_MANGA_TITLE, ignoreCase = true) }
		}
	}

	private fun scoreDetailsResponse(json: JSONObject): Int {
		val info = json.optJSONObject("info") ?: return DETAILS_SCORE_EMPTY
		val chaptersCount = json.optJSONArray("chapters")?.length() ?: 0
		if (chaptersCount > 0) {
			return DETAILS_SCORE_WITH_CHAPTERS
		}
		val author = info.optString("author").trim()
		val description = info.optString("description").trim()
		val cover = info.optString("cover").trim().lowercase(Locale.ROOT)
		if (author.isNotEmpty() && !author.equals("Auteur Inconnu", ignoreCase = true)) {
			return DETAILS_SCORE_WITH_METADATA
		}
		if (description.isNotEmpty()) {
			return DETAILS_SCORE_WITH_METADATA
		}
		if (cover.isNotEmpty() && "logo-luffy" !in cover) {
			return DETAILS_SCORE_WITH_METADATA
		}
		return DETAILS_SCORE_EMPTY
	}

	private fun extractScanFolder(url: String): String? {
		val httpUrl = url.toAbsoluteUrl(domain).toHttpUrlOrNull() ?: return null
		httpUrl.queryParameter("scan")?.takeIf { it.isNotBlank() }?.let { return it }
		val scanIndex = httpUrl.pathSegments.indexOf("scan")
		if (scanIndex != -1 && scanIndex + 1 < httpUrl.pathSegments.size) {
			return httpUrl.pathSegments[scanIndex + 1].takeIf { it.isNotBlank() }
		}
		return null
	}

	private fun extractSlugFromMangaUrl(url: String): String? {
		if (url.startsWith("/manga/")) {
			return url.substringAfter("/manga/").substringBefore('/').takeIf { it.isNotBlank() }
		}
		val httpUrl = url.toHttpUrlOrNull() ?: return null
		val mangaIndex = httpUrl.pathSegments.indexOf("manga")
		if (mangaIndex != -1 && mangaIndex + 1 < httpUrl.pathSegments.size) {
			return httpUrl.pathSegments[mangaIndex + 1].takeIf { it.isNotBlank() }
		}
		return null
	}

	private fun String.toMangaTitle(): String = URLDecoder.decode(this, StandardCharsets.UTF_8.name()).replace('+', ' ').trim()

	private fun String.toMangaSlug(): String = lowercase(Locale.ROOT)
		.trim()
		.replace(WHITESPACES_REGEX, "+")

	private fun legacyMangaTitle(url: String): String? = when (url.uppercase(Locale.ROOT)) {
		"OP" -> "One Piece"
		"LCDL" -> "Les Carnets de l'apothicaire"
		"JKM" -> "Jujutsu Kaisen Modulo"
		"OPC" -> "One Piece Colo"
		"LDS" -> "L'Atelier des Sorciers"
		else -> null
	}

	private companion object {

		private const val API_LIMIT = 12
		private const val UNKNOWN_MANGA_TITLE = "Unknown manga"
		private const val DETAILS_SCORE_EMPTY = 0
		private const val DETAILS_SCORE_WITH_METADATA = 1
		private const val DETAILS_SCORE_WITH_CHAPTERS = 2
		private val AUTHORS_SPLIT_PATTERN = Regex("[,&/]")
		private val WHITESPACES_REGEX = Regex("\\s+")
		private val CHAPTER_NUMBER_REGEX = Regex("(\\d+(?:\\.\\d+)?)$")
		private val IMAGE_MTIMES_BLOCK = Regex("imageMtimes\\s*=\\s*\\{([^}]*)}")
		private val IMAGE_MTIMES_ENTRY = Regex("[\"']?([0-9]+)[\"']?\\s*:\\s*([0-9]+)")
	}
}
