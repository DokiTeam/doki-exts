package org.dokiteam.doki.parsers.site.fr

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.search.MangaSearchQuery
import kotlin.time.Duration.Companion.minutes

internal class PoseidonScansChapterPagesSmokeTest {

	@Test
	fun verifyChapterPagesRecovery() = runTest(timeout = 3.minutes) {
		val parser = MangaLoaderContextMock.newParserInstance(MangaParserSource.POSEIDONSCANS)
		val list = parser.getList(MangaSearchQuery.EMPTY)
		check(list.isNotEmpty()) { "Poseidon list is empty" }

		val detailedManga = list.take(8).firstNotNullOfOrNull { manga ->
			runCatching {
				parser.getDetails(manga).takeIf { !it.chapters.isNullOrEmpty() }
			}.getOrNull()
		} ?: error("Unable to load a manga with chapters from Poseidon list")

		val chapters = detailedManga.chapters.orEmpty()
		check(chapters.isNotEmpty()) { "No chapters found for ${detailedManga.publicUrl}" }

		val sampleChapters = buildList<MangaChapter> {
			add(chapters.first())
			chapters.getOrNull(chapters.size / 2)?.let { add(it) }
			add(chapters.last())
		}.distinctBy { it.id }

		val chapterPageCounts = sampleChapters.map { chapter ->
			val pages = parser.getPages(chapter)
			val pageUrls = pages.map { it.url }.distinct()
			println("POSEIDON_SMOKE|chapter=${chapter.url}|pages=${pageUrls.size}|first=${pageUrls.firstOrNull()}")
			check(pageUrls.size > 1) { "Expected more than one page for ${chapter.url}, got ${pageUrls.size}" }
			check(pageUrls.none { it.contains("/api/covers/") }) {
				"Chapter pages fallbacked to cover for ${chapter.url}: $pageUrls"
			}
			chapter.url to pageUrls.size
		}

		println(
			"POSEIDON_SMOKE_SUMMARY|manga=${detailedManga.publicUrl}|totalChapters=${chapters.size}|sample=$chapterPageCounts",
		)
	}
}
