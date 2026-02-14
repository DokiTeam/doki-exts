package org.dokiteam.doki.parsers.site.all

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.SortOrder
import kotlin.time.Duration.Companion.minutes

internal class MangaPlusFrenchFullVerificationTest {

	@Test
	fun verifyMangaPlusFrenchParserEndToEnd() = runTest(timeout = 6.minutes) {
		val parser = MangaLoaderContextMock.newParserInstance(MangaParserSource.MANGAPLUSPARSER_FR)

		val popular = parser.getList(offset = 0, order = SortOrder.POPULARITY, filter = MangaListFilter.EMPTY)
		check(popular.isNotEmpty()) { "Popularity list is empty" }

		val updated = parser.getList(offset = 0, order = SortOrder.UPDATED, filter = MangaListFilter.EMPTY)
		check(updated.isNotEmpty()) { "Updated list is empty" }

		val alphabetical = parser.getList(offset = 0, order = SortOrder.ALPHABETICAL, filter = MangaListFilter.EMPTY)
		check(alphabetical.isNotEmpty()) { "Alphabetical list is empty" }

		val query = popular.first().title
			.split(Regex("\\s+"))
			.firstOrNull { it.length >= 3 }
			?: popular.first().title.take(4)
		val search = parser.getList(
			offset = 0,
			order = SortOrder.UPDATED,
			filter = MangaListFilter(query = query),
		)
		check(search.isNotEmpty()) { "Search returned empty list for query '$query'" }

		val detailsCandidates = (popular + updated + alphabetical)
			.distinctBy { it.id }
			.take(40)
			.mapNotNull { manga -> runCatching { parser.getDetails(manga) }.getOrNull() }
			.filter { !it.chapters.isNullOrEmpty() }
		check(detailsCandidates.isNotEmpty()) { "Unable to load detailed manga with chapters" }
		val detailed = detailsCandidates.maxByOrNull { it.chapters.orEmpty().size }
			?: error("No detailed manga selected")

		check(!detailed.coverUrl.isNullOrBlank()) { "Cover URL is blank for ${detailed.publicUrl}" }
		checkImageResponse(detailed.coverUrl)
		check(!detailed.description.isNullOrBlank()) { "Description is blank for ${detailed.publicUrl}" }

		val chapters = detailed.chapters.orEmpty()
		check(chapters.size > 1) { "Not enough chapters for ${detailed.publicUrl}" }
		check(chapters.distinctBy { it.id }.size == chapters.size) { "Duplicate chapter IDs for ${detailed.publicUrl}" }

		val readableSamples = mutableListOf<Pair<MangaChapter, List<String>>>()
		for (chapter in chapters) {
			if (readableSamples.size >= 3) break
			val pages = runCatching { parser.getPages(chapter) }.getOrNull().orEmpty()
			val pageUrls = pages.map { parser.getPageUrl(it) }.distinct()
			if (pageUrls.isEmpty()) continue
			readableSamples += chapter to pageUrls
		}
		check(readableSamples.isNotEmpty()) { "No readable chapters found for ${detailed.publicUrl}" }

		val sampleSummary = readableSamples.map { (chapter, pageUrls) ->
			check(pageUrls.all { it.startsWith("http://") || it.startsWith("https://") }) {
				"Non-absolute page URLs for ${chapter.url}: $pageUrls"
			}
			checkImageResponse(pageUrls.first())
			checkImageResponse(pageUrls.last())
			val cachedPagesCount = parser.getPages(chapter).size
			check(cachedPagesCount == pageUrls.size) {
				"Page count mismatch between repeated calls for ${chapter.url}: ${pageUrls.size} != $cachedPagesCount"
			}
			chapter.url to pageUrls.size
		}

		println(
			"MANGAPLUSFR_FULL_SMOKE|popular=${popular.size}|updated=${updated.size}|alpha=${alphabetical.size}|" +
				"search=${search.size}|manga=${detailed.publicUrl}|chapters=${chapters.size}|samples=$sampleSummary",
		)
	}

	private suspend fun checkImageResponse(url: String?) {
		check(!url.isNullOrBlank()) { "Image URL is blank" }
		MangaLoaderContextMock.doRequest(url, MangaParserSource.MANGAPLUSPARSER_FR).use { response ->
			check(response.isSuccessful) { "Image request failed ${response.code}(${response.message}) for $url" }
			val contentType = response.header("Content-Type").orEmpty()
			check(contentType.startsWith("image/")) {
				"Invalid image Content-Type '$contentType' for $url"
			}
		}
	}
}
