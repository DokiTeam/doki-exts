package org.dokiteam.doki.parsers.site.fr

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONArray
import org.json.JSONObject
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.core.PagedMangaParser
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
import org.dokiteam.doki.parsers.util.json.getStringOrNull
import org.dokiteam.doki.parsers.util.json.mapJSONNotNull
import org.dokiteam.doki.parsers.util.parseJson
import org.dokiteam.doki.parsers.util.parseSafe
import org.dokiteam.doki.parsers.util.suspendlazy.suspendLazy
import org.dokiteam.doki.parsers.util.toAbsoluteUrl
import java.text.SimpleDateFormat
import java.util.EnumSet
import java.util.LinkedHashSet
import java.util.Locale
import java.util.TimeZone
import kotlin.text.toBigDecimalOrNull

@MangaSourceParser("PUNKRECORDZ", "PunkRecordz", "fr")
internal class PunkRecordz(context: MangaLoaderContext) :
	PagedMangaParser(context, MangaParserSource.PUNKRECORDZ, pageSize = 20) {

	override val configKeyDomain = ConfigKey.Domain("punkrecordz.com")

	override val defaultSortOrder: SortOrder = SortOrder.UPDATED

	override val availableSortOrders: Set<SortOrder> = EnumSet.of(
		SortOrder.UPDATED,
		SortOrder.UPDATED_ASC,
		SortOrder.NEWEST,
		SortOrder.NEWEST_ASC,
		SortOrder.ALPHABETICAL,
		SortOrder.ALPHABETICAL_DESC,
		SortOrder.RELEVANCE,
	)

	override val filterCapabilities: MangaListFilterCapabilities = MangaListFilterCapabilities(
		isSearchSupported = true,
		isSearchWithFiltersSupported = true,
		isMultipleTagsSupported = true,
		isTagsExclusionSupported = true,
		isAuthorSearchSupported = true,
	)

	private val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
		timeZone = TimeZone.getTimeZone("UTC")
	}

	private val allMangaCache = suspendLazy { fetchAllManga() }
	private val mangaBySlugCache = HashMap<String, CatalogManga>()
	private val chaptersBySlugCache = HashMap<String, List<MangaChapter>>()
	private val pagesByCacheKey = HashMap<String, List<MangaPage>>()

	override suspend fun getFilterOptions(): MangaListFilterOptions {
		val mangas = allMangaCache.get()
		val tags = mangas
			.flatMap { it.tags }
			.sortedBy { it.title.lowercase(Locale.ROOT) }
			.toCollection(LinkedHashSet())
		val states = EnumSet.noneOf(MangaState::class.java).apply {
			mangas.forEach { item ->
				item.state?.let(::add)
			}
		}
		return MangaListFilterOptions(
			availableTags = if (tags.isNotEmpty()) {
				tags
			} else {
				setOf(
					MangaTag(
						key = ALL_TAG_KEY,
						title = "General",
						source = source,
					),
				)
			},
			availableStates = states,
			availableContentTypes = EnumSet.of(ContentType.MANGA),
		)
	}

	override suspend fun getListPage(page: Int, order: SortOrder, filter: MangaListFilter): List<Manga> {
		val filtered = filterManga(allMangaCache.get(), filter)
		val sorted = sortManga(filtered, order, filter.query)
		val fromIndex = ((page - 1) * pageSize).coerceAtLeast(0)
		if (fromIndex >= sorted.size) {
			return emptyList()
		}
		val toIndex = (fromIndex + pageSize).coerceAtMost(sorted.size)
		return sorted.subList(fromIndex, toIndex).map { it.toManga() }
	}

	override suspend fun getDetails(manga: Manga): Manga {
		val slug = extractSlug(manga.url) ?: extractSlug(manga.publicUrl) ?: return manga
		val mangaData = fetchMangaBySlugCached(slug)
		val chapters = fetchChaptersCached(slug)
		return manga.copy(
			title = mangaData?.title ?: manga.title,
			coverUrl = mangaData?.coverUrl ?: manga.coverUrl,
			tags = mangaData?.tags ?: manga.tags,
			state = mangaData?.state ?: manga.state,
			description = mangaData?.description ?: manga.description ?: "",
			chapters = chapters,
		)
	}

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val chapterId = chapter.url.toAbsoluteUrl(domain).toHttpUrlOrNull()?.queryParameter("id")
		val cacheKey = if (chapterId.isNullOrBlank()) chapter.url else "id:$chapterId"
		synchronized(pagesByCacheKey) {
			pagesByCacheKey[cacheKey]?.let { return it }
		}
		val pagesJson = if (!chapterId.isNullOrBlank()) {
			fetchPagesByChapterId(chapterId)
		} else {
			val slug = extractSlug(chapter.url)
			val chapterNumber = extractChapterNumber(chapter.url)
			if (slug.isNullOrBlank() || chapterNumber == null) {
				JSONArray()
			} else {
				fetchPagesBySlugAndNumber(slug, chapterNumber)
			}
		}

		val pages = pagesJson.mapJSONNotNull { pageJson ->
			val image = pageJson.getStringOrNull("colored") ?: pageJson.getStringOrNull("original")
			val pageUrl = buildImageUrl(image) ?: return@mapJSONNotNull null
			MangaPage(
				id = generateUid(pageUrl),
				url = pageUrl,
				preview = null,
				source = source,
			)
		}
		if (pages.isNotEmpty()) {
			synchronized(pagesByCacheKey) {
				pagesByCacheKey[cacheKey] = pages
			}
		}
		return pages
	}

	private suspend fun fetchAllManga(): List<CatalogManga> {
		val result = ArrayList<CatalogManga>()
		var skip = 0
		while (true) {
			val data = graphQl(
				query = MANGAS_QUERY,
				variables = JSONObject().apply {
					put("skip", skip)
					put("limit", CATALOG_PAGE_LIMIT)
					put("order", -1)
				},
			)
			val mangasArray = data.optJSONArray("mangas") ?: break
			if (mangasArray.length() == 0) {
				break
			}
			result.addAll(mangasArray.mapJSONNotNull(::parseMangaJson))
			skip += mangasArray.length()
			if (mangasArray.length() < CATALOG_PAGE_LIMIT) {
				break
			}
		}
		return result.distinctBy { it.slug.lowercase(Locale.ROOT) }
	}

	private suspend fun fetchMangaBySlug(slug: String): CatalogManga? {
		val data = graphQl(
			query = MANGA_QUERY,
			variables = JSONObject().apply {
				put("slug", slug)
			},
		)
		val manga = data.optJSONObject("manga") ?: return null
		return parseMangaJson(manga)
	}

	private suspend fun fetchMangaBySlugCached(slug: String): CatalogManga? {
		synchronized(mangaBySlugCache) {
			mangaBySlugCache[slug]?.let { return it }
		}
		val fetched = fetchMangaBySlug(slug) ?: return null
		synchronized(mangaBySlugCache) {
			mangaBySlugCache[slug] = fetched
		}
		return fetched
	}

	private suspend fun fetchChapters(slug: String): List<MangaChapter> {
		val result = ArrayList<MangaChapter>()
		val seenNumbers = HashSet<String>()
		var skip = 0
		while (true) {
			val data = graphQl(
				query = CHAPTERS_QUERY,
				variables = JSONObject().apply {
					put("slug", slug)
					put("limit", CHAPTERS_PAGE_LIMIT)
					put("skip", skip)
					put("order", -1)
				},
			)
			val chaptersArray = data.optJSONArray("chapters") ?: break
			if (chaptersArray.length() == 0) {
				break
			}
			val pageChapters = chaptersArray.mapJSONNotNull { chapterJson ->
				val chapterId = chapterJson.getStringOrNull("id")?.takeIf { it.isNotBlank() } ?: return@mapJSONNotNull null
				val chapterNumber = parseChapterNumber(chapterJson.opt("number")) ?: return@mapJSONNotNull null
				val chapterNumberLabel = chapterNumber.second
				if (!seenNumbers.add(chapterNumberLabel)) {
					return@mapJSONNotNull null
				}
				val uploadDate = parseDate(
					chapterJson.getStringOrNull("updateTime") ?: chapterJson.getStringOrNull("insertTime"),
				)
				val chapterUrl = "/mangas/$slug/$chapterNumberLabel?id=$chapterId"
				MangaChapter(
					id = generateUid("$slug#$chapterId"),
					title = "Chapitre $chapterNumberLabel",
					number = chapterNumber.first,
					volume = 0,
					url = chapterUrl,
					scanlator = null,
					uploadDate = uploadDate,
					branch = null,
					source = source,
				)
			}
			result.addAll(pageChapters)
			skip += chaptersArray.length()
			if (chaptersArray.length() < CHAPTERS_PAGE_LIMIT) {
				break
			}
		}
		return result
	}

	private suspend fun fetchChaptersCached(slug: String): List<MangaChapter> {
		synchronized(chaptersBySlugCache) {
			chaptersBySlugCache[slug]?.let { return it }
		}
		val fetched = fetchChapters(slug)
		synchronized(chaptersBySlugCache) {
			chaptersBySlugCache[slug] = fetched
		}
		return fetched
	}

	private suspend fun fetchPagesByChapterId(chapterId: String): JSONArray {
		val data = graphQl(
			query = CHAPTER_BY_ID_QUERY,
			variables = JSONObject().apply {
				put("id", chapterId)
			},
		)
		val chapter = data.optJSONObject("chapter") ?: return JSONArray()
		if (chapter.optBoolean("deleted", false) || !chapter.optBoolean("published", true)) {
			return JSONArray()
		}
		return chapter.optJSONArray("pages") ?: JSONArray()
	}

	private suspend fun fetchPagesBySlugAndNumber(slug: String, chapterNumber: Double): JSONArray {
		val data = graphQl(
			query = CHAPTER_PAGES_QUERY,
			variables = JSONObject().apply {
				put("slug", slug)
				put("number", chapterNumber)
			},
		)
		val chaptersArray = data.optJSONArray("chapters") ?: return JSONArray()
		val chapter = chaptersArray.optJSONObject(0) ?: return JSONArray()
		if (chapter.optBoolean("deleted", false) || !chapter.optBoolean("published", true)) {
			return JSONArray()
		}
		return chapter.optJSONArray("pages") ?: JSONArray()
	}

	private suspend fun graphQl(query: String, variables: JSONObject = JSONObject()): JSONObject {
		val body = JSONObject().apply {
			put("query", query)
			put("variables", variables)
		}
		val response = webClient.httpPost(apiUrl, body).parseJson()
		val errors = response.optJSONArray("errors")
		if (errors != null && errors.length() > 0) {
			val message = errors.optJSONObject(0)?.optString("message")?.takeIf { it.isNotBlank() }
				error("PunkRecordz GraphQL error: ${message ?: "Unknown error"}")
		}
		return response.optJSONObject("data") ?: JSONObject()
	}

	private fun parseMangaJson(mangaJson: JSONObject): CatalogManga? {
		val slug = mangaJson.getStringOrNull("slug")?.trim()?.takeIf { it.isNotEmpty() } ?: return null
		val title = mangaJson.getStringOrNull("name")?.trim()?.takeIf { it.isNotEmpty() } ?: return null
		val tags = parseTags(mangaJson.getStringOrNull("keywords"))
		val story = mangaJson.getStringOrNull("story")?.trim()?.takeIf { it.isNotEmpty() }
		val status = mangaJson.getStringOrNull("status")?.trim()?.takeIf { it.isNotEmpty() }
		return CatalogManga(
			slug = slug,
			title = title,
			coverUrl = buildImageUrl(mangaJson.getStringOrNull("thumb")),
			tags = tags,
			tagKeys = tags.mapTo(HashSet(tags.size)) { it.key.lowercase(Locale.ROOT) },
			state = parseState(mangaJson.optInt("statusProgression", -1)),
			description = story ?: status,
			keywords = mangaJson.getStringOrNull("keywords"),
			status = status,
			updateTime = parseDate(mangaJson.getStringOrNull("updateTime")),
			insertTime = parseDate(mangaJson.getStringOrNull("insertTime")),
		)
	}

	private fun parseTags(rawKeywords: String?): Set<MangaTag> {
		if (rawKeywords.isNullOrBlank()) {
			return emptySet()
		}
		return rawKeywords
			.split(',')
			.mapNotNull { raw ->
				val title = raw.trim().replace(Regex("\\s+"), " ")
				if (title.isEmpty()) {
					return@mapNotNull null
				}
				val key = normalizeTagKey(title)
				if (key.isEmpty()) {
					return@mapNotNull null
				}
				MangaTag(
					key = key,
					title = title.replaceFirstChar {
						if (it.isLowerCase()) {
							it.titlecase(sourceLocale)
						} else {
							it.toString()
						}
					},
					source = source,
				)
			}
			.toSet()
	}

	private fun filterManga(mangas: List<CatalogManga>, filter: MangaListFilter): List<CatalogManga> {
		if (mangas.isEmpty()) {
			return mangas
		}
		val query = filter.query?.trim()?.lowercase(sourceLocale).orEmpty()
		val author = filter.author?.trim()?.lowercase(sourceLocale).orEmpty()
		val includeTags = filter.tags.mapTo(HashSet(filter.tags.size)) { it.key.lowercase(Locale.ROOT) }
		val excludeTags = filter.tagsExclude.mapTo(HashSet(filter.tagsExclude.size)) { it.key.lowercase(Locale.ROOT) }

		return mangas.filter { item ->
			if (query.isNotEmpty()) {
				val haystack = buildString {
					append(item.title)
					append('\n')
					append(item.slug)
					item.keywords?.let {
						append('\n')
						append(it)
					}
					item.status?.let {
						append('\n')
						append(it)
					}
				}.lowercase(sourceLocale)
				if (!haystack.contains(query)) {
					return@filter false
				}
			}

			if (author.isNotEmpty()) {
				val haystack = listOfNotNull(item.keywords, item.status).joinToString("\n").lowercase(sourceLocale)
				if (!haystack.contains(author)) {
					return@filter false
				}
			}

			if (filter.states.isNotEmpty() && item.state !in filter.states) {
				return@filter false
			}

			if (filter.types.isNotEmpty() && ContentType.MANGA !in filter.types) {
				return@filter false
			}

			if (includeTags.isNotEmpty() && ALL_TAG_KEY !in includeTags && item.tagKeys.none { it in includeTags }) {
				return@filter false
			}

			if (excludeTags.isNotEmpty() && ALL_TAG_KEY !in excludeTags && item.tagKeys.any { it in excludeTags }) {
				return@filter false
			}

			true
		}
	}

	private fun sortManga(mangas: List<CatalogManga>, order: SortOrder, query: String?): List<CatalogManga> {
		if (mangas.size < 2) {
			return mangas
		}
		return when (order) {
			SortOrder.UPDATED -> mangas.sortedByDescending { it.updateTime }
			SortOrder.UPDATED_ASC -> mangas.sortedBy { it.updateTime }
			SortOrder.NEWEST -> mangas.sortedByDescending { it.insertTime }
			SortOrder.NEWEST_ASC -> mangas.sortedBy { it.insertTime }
			SortOrder.ALPHABETICAL -> mangas.sortedBy { it.title.lowercase(sourceLocale) }
			SortOrder.ALPHABETICAL_DESC -> mangas.sortedByDescending { it.title.lowercase(sourceLocale) }
			SortOrder.RELEVANCE -> {
				val normalizedQuery = query?.trim()?.lowercase(sourceLocale).orEmpty()
				if (normalizedQuery.isEmpty()) {
					mangas.sortedByDescending { it.updateTime }
				} else {
					mangas.sortedWith(
						compareBy<CatalogManga> { relevanceScore(it, normalizedQuery) }
							.thenBy { it.title.length }
							.thenBy { it.title.lowercase(sourceLocale) },
					)
				}
			}
			else -> mangas
		}
	}

	private fun relevanceScore(manga: CatalogManga, query: String): Int {
		val title = manga.title.lowercase(sourceLocale)
		if (title == query) return 0
		if (title.startsWith(query)) return 1
		if (title.contains(query)) return 2
		return 3
	}

	private fun parseState(statusProgression: Int): MangaState? = when (statusProgression) {
		4 -> MangaState.FINISHED
		1, 2, 3 -> MangaState.ONGOING
		else -> null
	}

	private fun buildImageUrl(path: String?): String? {
		val value = path?.trim().orEmpty()
		if (value.isEmpty() || value == "null") {
			return null
		}
		if (value.startsWith("http://") || value.startsWith("https://")) {
			return value
		}
		val clean = value.removePrefix("/")
		return when {
			clean.startsWith("images/") -> "https://api.$domain/$clean"
			clean.startsWith("webp/") -> "https://api.$domain/images/$clean"
			else -> "https://api.$domain/images/webp/$clean.webp"
		}
	}

	private fun extractSlug(url: String): String? {
		val path = url.substringBefore('?').substringBefore('#')
		val after = path.substringAfter("/mangas/", "")
		if (after.isEmpty()) {
			return null
		}
		return after.substringBefore('/').ifBlank { null }
	}

	private fun extractChapterNumber(url: String): Double? {
		val path = url.substringBefore('?').substringBefore('#')
		val raw = path.substringAfterLast('/', "")
		if (raw.isEmpty()) {
			return null
		}
		return raw.toDoubleOrNull()
	}

	private fun parseChapterNumber(rawValue: Any?): Pair<Float, String>? {
		val raw = rawValue?.toString()?.trim()?.takeIf { it.isNotEmpty() && it != "null" } ?: return null
		val decimal = raw.toBigDecimalOrNull() ?: return null
		val normalized = decimal.stripTrailingZeros().toPlainString()
		return decimal.toFloat() to normalized
	}

	private fun normalizeTagKey(tag: String): String {
		val normalized = tag
			.lowercase(Locale.ROOT)
			.replace(Regex("[^\\p{Alnum}]+"), "-")
			.trim('-')
		return normalized
	}

	private fun parseDate(date: String?): Long {
		if (date.isNullOrBlank()) {
			return 0L
		}
		return isoDateFormat.parseSafe(date)
	}

	private val apiUrl: String
		get() = "https://api.$domain/graphql"

	private fun CatalogManga.toManga(): Manga = Manga(
		id = generateUid("/mangas/$slug"),
		title = title,
		altTitles = emptySet(),
		url = "/mangas/$slug",
		publicUrl = "/mangas/$slug".toAbsoluteUrl(domain),
		rating = RATING_UNKNOWN,
		contentRating = null,
		coverUrl = coverUrl,
		tags = tags,
		state = state,
		authors = emptySet(),
		description = description,
		source = source,
	)

	private data class CatalogManga(
		val slug: String,
		val title: String,
		val coverUrl: String?,
		val tags: Set<MangaTag>,
		val tagKeys: Set<String>,
		val state: MangaState?,
		val description: String?,
		val keywords: String?,
		val status: String?,
		val updateTime: Long,
		val insertTime: Long,
	)

	private companion object {
		const val CATALOG_PAGE_LIMIT = 200
		const val CHAPTERS_PAGE_LIMIT = 200
		const val ALL_TAG_KEY = "__all__"

		val MANGAS_QUERY: String = """
			query Mangas(${'$'}skip: Int!, ${'$'}limit: Int!, ${'$'}order: Float!) {
			  mangas(
			    skip: ${'$'}skip
			    limit: ${'$'}limit
			    where: { published: true, deleted: false }
			    order: [{ field: "updateTime", order: ${'$'}order }]
			  ) {
			    id
			    slug
			    name
			    thumb
			    keywords
			    story
			    status
			    statusProgression
			    updateTime
			    insertTime
			  }
			}
		""".trimIndent()

		val MANGA_QUERY: String = """
			query MangaBySlug(${'$'}slug: String!) {
			  manga(where: { slug: ${'$'}slug, published: true, deleted: false }) {
			    id
			    slug
			    name
			    thumb
			    keywords
			    story
			    status
			    statusProgression
			    updateTime
			    insertTime
			  }
			}
		""".trimIndent()

		val CHAPTERS_QUERY: String = """
			query Chapters(${'$'}slug: String!, ${'$'}limit: Int!, ${'$'}skip: Int!, ${'$'}order: Float!) {
			  chapters(
			    limit: ${'$'}limit
			    skip: ${'$'}skip
			    where: {
			      deleted: false
			      published: true
			      manga: { slug: ${'$'}slug, published: true, deleted: false }
			    }
			    order: [{ field: "number", order: ${'$'}order }]
			  ) {
			    id
			    number
			    updateTime
			    insertTime
			  }
			}
		""".trimIndent()

		val CHAPTER_BY_ID_QUERY: String = """
			query ChapterById(${'$'}id: String!) {
			  chapter(where: { id: ${'$'}id }) {
			    id
			    deleted
			    published
			    manga {
			      id
			    }
			    pages {
			      original
			      colored
			    }
			  }
			}
		""".trimIndent()

		val CHAPTER_PAGES_QUERY: String = """
			query ChapterPages(${'$'}slug: String!, ${'$'}number: Float!) {
			  chapters(
			    limit: 1
			    skip: 0
			    where: {
			      number: ${'$'}number
			      deleted: false
			      published: true
			      manga: { slug: ${'$'}slug, published: true, deleted: false }
			    }
			    order: [{ field: "number", order: -1 }]
			  ) {
			    id
			    deleted
			    published
			    pages {
			      original
			      colored
			    }
			  }
			}
		""".trimIndent()
	}
}
