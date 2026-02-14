package org.dokiteam.doki.parsers.site.fr

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.SortOrder
import java.util.Locale
import kotlin.time.Duration.Companion.minutes

internal class BigSoloFullVerificationTest {

	@Test
	fun verifyBigSoloParserEndToEnd() = runTest(timeout = 6.minutes) {
		val parser = MangaLoaderContextMock.newParserInstance(MangaParserSource.BIGSOLO)

		val updated = parser.getList(offset = 0, order = SortOrder.UPDATED, filter = MangaListFilter.EMPTY)
		check(updated.isNotEmpty()) { "Updated list is empty" }
		val alphabetical = parser.getList(offset = 0, order = SortOrder.ALPHABETICAL, filter = MangaListFilter.EMPTY)
		check(alphabetical.isNotEmpty()) { "Alphabetical list is empty" }
		val expectedAlpha = alphabetical.sortedBy { it.title.lowercase(Locale.ROOT) }.map { it.id }
		check(alphabetical.map { it.id } == expectedAlpha) { "Alphabetical order is incorrect" }

		val options = parser.getFilterOptions()
		check(options.availableTags.isNotEmpty()) { "No tags in filter options" }

		val detailedCandidates = updated
			.take(20)
			.mapNotNull { manga -> runCatching { parser.getDetails(manga) }.getOrNull() }
		check(detailedCandidates.isNotEmpty()) { "Unable to load details from updated list" }
		val detailed = detailedCandidates.maxByOrNull { it.chapters.orEmpty().size }
			?: error("No detailed manga candidate")

		check(!detailed.coverUrl.isNullOrBlank()) { "Cover URL is blank for ${detailed.publicUrl}" }
		checkImageResponse(detailed.coverUrl)

		val chapters = detailed.chapters.orEmpty()
		check(chapters.size > 1) { "Too few chapters for ${detailed.publicUrl}: ${chapters.size}" }
		check(chapters.distinctBy { it.id }.size == chapters.size) {
			"Duplicate chapter ids for ${detailed.publicUrl}"
		}
		check(chapters.zipWithNext().all { (left, right) -> left.number <= right.number }) {
			"Chapter order is not ascending for ${detailed.publicUrl}"
		}

		val sampled = buildList<MangaChapter> {
			add(chapters.first())
			chapters.getOrNull(chapters.size / 2)?.let { add(it) }
			add(chapters.last())
		}.distinctBy { it.id }

		val sampleSummary = sampled.map { chapter ->
			val pages = parser.getPages(chapter)
			val pageUrls = pages.map { parser.getPageUrl(it) }.distinct()
			check(pageUrls.isNotEmpty()) { "No pages for ${chapter.url}" }
			check(pageUrls.all { it.startsWith("http://") || it.startsWith("https://") }) {
				"Non-absolute page URL for ${chapter.url}: $pageUrls"
			}
			checkImageResponse(pageUrls.first())
			checkImageResponse(pageUrls.last())
			chapter.url to pageUrls.size
		}

		println(
			"BIGSOLO_FULL_SMOKE|updated=${updated.size}|alpha=${alphabetical.size}|manga=${detailed.publicUrl}|" +
				"chapters=${chapters.size}|samples=$sampleSummary",
		)
	}

	private suspend fun checkImageResponse(url: String?) {
		check(!url.isNullOrBlank()) { "Image URL is blank" }
		MangaLoaderContextMock.doRequest(url, MangaParserSource.BIGSOLO).use { response ->
			check(response.isSuccessful) { "Image request failed ${response.code}(${response.message}) for $url" }
			val contentType = response.header("Content-Type").orEmpty()
			check(contentType.startsWith("image/")) {
				"Invalid image Content-Type '$contentType' for $url"
			}
		}
	}
}
