package org.dokiteam.doki.parsers.site.fr

import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.SortOrder
import kotlin.time.Duration.Companion.minutes

internal class PoseidonScansFullVerificationTest {

	private val source = MangaParserSource.POSEIDONSCANS
	private val mangaRouteRegex = Regex("""(?:https?://[^/]+)?/serie/([^/?#]+)""")
	private val chapterRouteRegex = Regex("""(?:https?://[^/]+)?/serie/([^/]+)/chapter/([^/?#]+)""")

	@Test
	fun verifyPoseidonScanParserEndToEnd() = runTest(timeout = 5.minutes) {
		val parser = MangaLoaderContextMock.newParserInstance(source)

		val updated = parser.getList(offset = 0, order = SortOrder.UPDATED, filter = MangaListFilter.EMPTY)
		check(updated.isNotEmpty()) { "Updated list is empty" }
		val popularity = parser.getList(offset = 0, order = SortOrder.POPULARITY, filter = MangaListFilter.EMPTY)
		check(popularity.isNotEmpty()) { "Popularity list is empty" }
		val alphabetical = parser.getList(offset = 0, order = SortOrder.ALPHABETICAL, filter = MangaListFilter.EMPTY)
		check(alphabetical.isNotEmpty()) { "Alphabetical list is empty" }
		val rating = parser.getList(offset = 0, order = SortOrder.RATING, filter = MangaListFilter.EMPTY)
		check(rating.isNotEmpty()) { "Rating list is empty" }

		val alphabeticalExpected = alphabetical.sortedBy { it.title.lowercase() }.map { it.id }
		check(alphabetical.map { it.id } == alphabeticalExpected) { "Alphabetical order is incorrect" }

		val sampleForQuery = updated.first()
		val query = sampleForQuery.title
			.split(Regex("\\s+"))
			.firstOrNull { it.length >= 3 }
			?: sampleForQuery.title.take(4)
		val searchResults = parser.getList(
			offset = 0,
			order = SortOrder.UPDATED,
			filter = MangaListFilter(query = query),
		)
		check(searchResults.isNotEmpty()) { "Search by query '$query' is empty" }

		val filterOptions = parser.getFilterOptions()
		check(filterOptions.availableTags.isNotEmpty()) { "No tags returned by getFilterOptions()" }
		val (tag, tagResults) = filterOptions.availableTags
			.take(12)
			.firstNotNullOfOrNull { tag ->
				val list = parser.getList(
					offset = 0,
					order = SortOrder.UPDATED,
					filter = MangaListFilter(tags = setOf(tag)),
				)
				if (list.isEmpty()) {
					null
				} else {
					tag to list
				}
			}
			?: error("Failed to find a tag that returns mangas")
		check(tagResults.all { manga -> manga.tags.contains(tag) }) {
			"Tag filtering is inconsistent for '${tag.title}'"
		}

		val detailsCandidates = updated
			.take(20)
			.mapNotNull { manga -> runCatching { parser.getDetails(manga) }.getOrNull() }
		check(detailsCandidates.isNotEmpty()) { "Unable to load manga details from Poseidon" }
		check(detailsCandidates.any { !it.description.isNullOrBlank() }) {
			"All tested manga descriptions are blank in top 20 detailed entries"
		}
		val detailed = detailsCandidates
			.filter { it.chapters.orEmpty().size > 2 }
			.maxByOrNull { it.chapters.orEmpty().size }
			?: error("Unable to load manga details from Poseidon")
		check(!detailed.coverUrl.isNullOrBlank()) { "Cover URL is blank for ${detailed.publicUrl}" }
		check(!detailed.description.isNullOrBlank()) { "Description is blank for ${detailed.publicUrl}" }
		checkImageResponse(detailed.coverUrl)

		val chapters = detailed.chapters.orEmpty()
		check(chapters.size > 2) { "Only ${chapters.size} chapters parsed for ${detailed.publicUrl}" }
		check(chapters.distinctBy { it.id }.size == chapters.size) { "Duplicate chapter ids for ${detailed.publicUrl}" }
		check(chapters.all { it.url.contains("/chapter/") }) { "Invalid chapter URLs for ${detailed.publicUrl}" }
		check(chapters.zipWithNext().all { (left, right) -> left.number <= right.number }) {
			"Chapter order is not ascending for ${detailed.publicUrl}"
		}

		val mangaSlug = parseMangaSlug(detailed.url)
			?: error("Unable to parse manga slug from ${detailed.url}")

		val sampledChapters = buildList<MangaChapter> {
			add(chapters.first())
			chapters.getOrNull(chapters.size / 2)?.let { add(it) }
			add(chapters.last())
		}.distinctBy { it.id }
		check(sampledChapters.isNotEmpty()) { "No chapters sampled for ${detailed.publicUrl}" }

		val sampledChapterPageCounts = sampledChapters.map { chapter ->
			val (chapterSlug, chapterNumberRaw) = parseChapterRoute(chapter.url)
				?: error("Unable to parse chapter route: ${chapter.url}")
			check(chapterSlug == mangaSlug) {
				"Chapter slug mismatch: chapter=$chapterSlug manga=$mangaSlug for ${chapter.url}"
			}

			val chapterMeta = requestJson("https://poseidon-scans.co/api/manga/$chapterSlug/$chapterNumberRaw")
			val chapterData = chapterMeta.optJSONObject("data")
				?.optJSONObject("chapterData")
				?: error("Missing chapterData from API for ${chapter.url}")
			val isPremium = chapterData.optBoolean("isPremium", false)
			check(!isPremium) { "Premium chapter leaked into readable list: ${chapter.url}" }
			val chapterId = chapterData.optString("id").takeIf { it.isNotBlank() }
				?: error("Missing chapter id for ${chapter.url}")

			val apiImagesPayload =
				requestJson("https://poseidon-scans.co/api/chapters/$chapterSlug/$chapterId/images")
			val apiImages = apiImagesPayload.optJSONArray("images")
				?: apiImagesPayload.optJSONObject("data")?.optJSONArray("images")
				?: error("Missing images array for ${chapter.url}")
			val apiImageCount = apiImages.length()
			check(apiImageCount > 1) { "API returned $apiImageCount image(s) for ${chapter.url}" }

			val parserPages = parser.getPages(chapter)
			val parserPageUrls = parserPages.map { parser.getPageUrl(it) }.distinct()
			check(parserPageUrls.size == apiImageCount) {
				"Pages mismatch for ${chapter.url}: parser=${parserPageUrls.size}, api=$apiImageCount"
			}
			check(parserPageUrls.none { it.contains("/api/covers/") }) {
				"Cover fallback detected in pages for ${chapter.url}: $parserPageUrls"
			}
			check(parserPageUrls.all { it.contains("/api/chapters/$chapterSlug/") }) {
				"Unexpected page URL pattern for ${chapter.url}: $parserPageUrls"
			}

			checkImageResponse(parserPageUrls.first())
			checkImageResponse(parserPageUrls.last())
			chapter.url to parserPageUrls.size
		}

		val updatedTop = updated.take(10).map { it.id }
		val popularityTop = popularity.take(10).map { it.id }
		println(
			"POSEIDON_FULL_SMOKE|updated=${updated.size}|popular=${popularity.size}|alpha=${alphabetical.size}|" +
				"rating=${rating.size}|top10_updated_eq_popular=${updatedTop == popularityTop}|" +
				"manga=${detailed.publicUrl}|chapters=${chapters.size}|tag=${tag.title}|samples=$sampledChapterPageCounts|" +
				"description_len=${detailed.description?.length ?: 0}",
		)
	}

	private fun parseMangaSlug(url: String): String? {
		val match = mangaRouteRegex.find(url) ?: return null
		return match.groupValues.getOrNull(1)?.trim()?.takeIf { it.isNotEmpty() }
	}

	private fun parseChapterRoute(url: String): Pair<String, String>? {
		val match = chapterRouteRegex.find(url) ?: return null
		val slug = match.groupValues.getOrNull(1)?.trim().orEmpty()
		val chapterNumberRaw = match.groupValues.getOrNull(2)?.trim().orEmpty()
		if (slug.isEmpty() || chapterNumberRaw.isEmpty()) return null
		return slug to chapterNumberRaw
	}

	private suspend fun requestJson(url: String): JSONObject {
		val rawBody = MangaLoaderContextMock.doRequest(url, source).use { response ->
			check(response.isSuccessful) { "Request failed ${response.code}(${response.message}) for $url" }
			response.body?.string().orEmpty()
		}
		return JSONObject(rawBody)
	}

	private suspend fun checkImageResponse(url: String?) {
		check(!url.isNullOrBlank()) { "Image url is blank" }
		MangaLoaderContextMock.doRequest(url, source).use { response ->
			check(response.isSuccessful) { "Image request failed ${response.code}(${response.message}) for $url" }
			val contentType = response.header("Content-Type").orEmpty()
			check(contentType.startsWith("image/")) {
				"Invalid image Content-Type '$contentType' for $url"
			}
		}
	}
}
