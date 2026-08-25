package org.dokiteam.doki.parsers.site.all

import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.MangaParser
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.RATING_UNKNOWN
import org.dokiteam.doki.parsers.util.json.asTypedList
import kotlin.time.Duration.Companion.minutes

internal class MangaPlusParserTest {

	private val context = MangaLoaderContextMock
	private val timeout = 2.minutes
	private val source = MangaParserSource.MANGAPLUSPARSER_EN
	private val titleId = "100280"

	@Test
	fun details_include_mid_chapters_and_load_pages() = runTest(timeout = timeout) {
		val parser = context.newParserInstance(source)
		val titleDetailView = getTitleDetailView(titleId)
		val chapterGroups = titleDetailView.getJSONArray("chapterListGroup").asTypedList<JSONObject>()
		val legacyChapterIds = chapterGroups.flatMap { group ->
			group.optJSONArray("firstChapterList")?.asTypedList<JSONObject>().orEmpty() +
				group.optJSONArray("lastChapterList")?.asTypedList<JSONObject>().orEmpty()
		}.map { it.getInt("chapterId").toString() }.toSet()
		val midChapterIds = chapterGroups.flatMap { group ->
			group.optJSONArray("midChapterList")?.asTypedList<JSONObject>().orEmpty()
		}.map { it.getInt("chapterId").toString() }.toSet()

		assertTrue(legacyChapterIds.isNotEmpty(), "Legacy chapter lists are empty")
		assertTrue(midChapterIds.isNotEmpty(), "No chapters found in midChapterList for title $titleId")

		val manga = org.dokiteam.doki.parsers.model.Manga(
			id = 100280L,
			url = titleId,
			publicUrl = "https://mangaplus.shueisha.co.jp/titles/$titleId",
			title = "Hope You're Happy, Lemon",
			coverUrl = "",
			altTitles = emptySet(),
			authors = emptySet(),
			contentRating = null,
			rating = RATING_UNKNOWN,
			state = null,
			source = source,
			tags = emptySet(),
		)
		val chapters = checkNotNull(parser.getDetails(manga).chapters) { "Chapters are null for title $titleId" }
		val parsedChapterIds = chapters.map { it.url }.toSet()
		assertTrue(chapters.size == 116, "Expected 116 chapters, got ${chapters.size}")

		assertTrue(
			parsedChapterIds.containsAll(legacyChapterIds),
			"Parser missed chapters from first/last lists",
		)
		assertTrue(
			parsedChapterIds.containsAll(midChapterIds),
			"Parser missed chapters from midChapterList: ${midChapterIds - parsedChapterIds}",
		)

		val loadedChapter = loadFirstReadableChapter(parser, chapters, preferredIds = legacyChapterIds)
		assertNotNull(loadedChapter, "No readable chapter could be loaded for title $titleId")
	}

	private suspend fun getTitleDetailView(titleId: String): JSONObject {
		val url = "https://jumpg-webapi.tokyo-cdn.com/api/title_detailV3?title_id=$titleId&format=json"
		return context.doRequest(url, source).use { response ->
			assertTrue(response.isSuccessful, "Title detail request failed: ${response.code} ${response.message}")
			JSONObject(checkNotNull(response.body).string())
				.getJSONObject("success")
				.getJSONObject("titleDetailView")
		}
	}

	private suspend fun loadFirstReadableChapter(
		parser: MangaParser,
		chapters: List<MangaChapter>,
		preferredIds: Set<String>,
	): MangaChapter? {
		val candidates = chapters.filter { it.url in preferredIds } + chapters.filterNot { it.url in preferredIds }
		for (chapter in candidates) {
			val pages = runCatching { parser.getPages(chapter) }.getOrNull().orEmpty()
			if (pages.isNotEmpty()) {
				return chapter
			}
		}
		return null
	}
}
