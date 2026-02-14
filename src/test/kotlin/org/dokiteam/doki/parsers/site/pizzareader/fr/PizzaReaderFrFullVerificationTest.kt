package org.dokiteam.doki.parsers.site.pizzareader.fr

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.SortOrder
import java.util.Locale
import kotlin.time.Duration.Companion.minutes

internal class PizzaReaderFrFullVerificationTest {

	@Test
	fun verifyBlueSoloParserEndToEnd() = runTest(timeout = 6.minutes) {
		verifySource(MangaParserSource.BLUESOLO, "BLUESOLO_FULL_SMOKE")
	}

	@Test
	fun verifyFmTeamParserEndToEnd() = runTest(timeout = 6.minutes) {
		verifySource(MangaParserSource.FMTEAM, "FMTEAM_FULL_SMOKE")
	}

	private suspend fun verifySource(source: MangaParserSource, smokeLabel: String) {
		val parser = MangaLoaderContextMock.newParserInstance(source)

		val updated = parser.getList(offset = 0, order = SortOrder.UPDATED, filter = MangaListFilter.EMPTY)
		check(updated.isNotEmpty()) { "Updated list is empty for $source" }

		val alphabetical = parser.getList(offset = 0, order = SortOrder.ALPHABETICAL, filter = MangaListFilter.EMPTY)
		check(alphabetical.isNotEmpty()) { "Alphabetical list is empty for $source" }
		val expectedAlpha = alphabetical.sortedBy { it.title.lowercase(Locale.ROOT) }.map { it.id }
		check(alphabetical.map { it.id } == expectedAlpha) { "Alphabetical order is incorrect for $source" }

		val query = updated.first().title
			.split(Regex("\\s+"))
			.firstOrNull { it.length >= 3 }
			?: updated.first().title.take(4)
		val search = parser.getList(
			offset = 0,
			order = SortOrder.UPDATED,
			filter = MangaListFilter(query = query),
		)
		check(search.isNotEmpty()) { "Search returned empty list for $source query '$query'" }

		val detailsCandidates = (updated + alphabetical)
			.distinctBy { it.id }
			.take(30)
			.mapNotNull { manga -> runCatching { parser.getDetails(manga) }.getOrNull() }
			.filter { !it.chapters.isNullOrEmpty() }
		check(detailsCandidates.isNotEmpty()) { "Unable to load detailed manga with chapters for $source" }
		val detailed = detailsCandidates.maxByOrNull { it.chapters.orEmpty().size }
			?: error("No detailed manga selected for $source")

		check(!detailed.coverUrl.isNullOrBlank()) { "Cover URL is blank for ${detailed.publicUrl}" }
		checkImageResponse(detailed.coverUrl, source)
		check(!detailed.description.isNullOrBlank()) { "Description is blank for ${detailed.publicUrl}" }

		val chapters = detailed.chapters.orEmpty()
		check(chapters.size > 1) { "Not enough chapters for ${detailed.publicUrl}" }
		check(chapters.distinctBy { it.id }.size == chapters.size) { "Duplicate chapter IDs for ${detailed.publicUrl}" }
		check(chapters.zipWithNext().all { (left, right) -> left.number <= right.number }) {
			"Chapter order is not ascending for ${detailed.publicUrl}"
		}

		val readableSamples = mutableListOf<Pair<MangaChapter, List<String>>>()
		for (chapter in chapters) {
			if (readableSamples.size >= 3) break
			val pageUrls = runCatching {
				parser.getPages(chapter).map { parser.getPageUrl(it) }.distinct()
			}.getOrDefault(emptyList())
			if (pageUrls.isEmpty()) continue
			readableSamples += chapter to pageUrls
		}
		check(readableSamples.isNotEmpty()) { "No readable chapter found for ${detailed.publicUrl}" }

		val sampleSummary = readableSamples.map { (chapter, pageUrls) ->
			check(pageUrls.all { it.startsWith("http://") || it.startsWith("https://") }) {
				"Non-absolute page URL for ${chapter.url}: $pageUrls"
			}
			checkImageResponse(pageUrls.first(), source)
			checkImageResponse(pageUrls.last(), source)
			val cachedPagesCount = parser.getPages(chapter).size
			check(cachedPagesCount == pageUrls.size) {
				"Page count mismatch between repeated calls for ${chapter.url}: ${pageUrls.size} != $cachedPagesCount"
			}
			chapter.url to pageUrls.size
		}

		println(
			"$smokeLabel|updated=${updated.size}|alpha=${alphabetical.size}|search=${search.size}|" +
				"manga=${detailed.publicUrl}|chapters=${chapters.size}|samples=$sampleSummary",
		)
	}

	private suspend fun checkImageResponse(url: String?, source: MangaParserSource) {
		check(!url.isNullOrBlank()) { "Image URL is blank" }
		MangaLoaderContextMock.doRequest(url, source).use { response ->
			check(response.isSuccessful) { "Image request failed ${response.code}(${response.message}) for $url" }
			val contentType = response.header("Content-Type").orEmpty()
			check(contentType.startsWith("image/")) {
				"Invalid image Content-Type '$contentType' for $url"
			}
		}
	}
}
