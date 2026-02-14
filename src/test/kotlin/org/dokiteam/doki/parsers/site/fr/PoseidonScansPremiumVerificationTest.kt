package org.dokiteam.doki.parsers.site.fr

import kotlinx.coroutines.test.runTest
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.model.Manga
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.SortOrder
import kotlin.math.abs
import kotlin.time.Duration.Companion.minutes

internal class PoseidonScansPremiumVerificationTest {

	private val source = MangaParserSource.POSEIDONSCANS

	private data class LatestEntry(
		val slug: String,
		val title: String,
		val latestNumberRaw: String,
		val latestIsPremium: Boolean,
		val chaptersCount: Int,
	)

	private data class ChapterEntry(
		val numberRaw: String,
		val numberValue: Double,
		val isPremium: Boolean,
	)

	@Test
	fun verifyPremiumAndNonPremiumChapters() = runTest(timeout = 6.minutes) {
		val parser = MangaLoaderContextMock.newParserInstance(source)
		val latestEntries = fetchLatestEntries()

		val premiumCandidate = latestEntries
			.filter { it.latestIsPremium && it.chaptersCount > 1 }
			.sortedBy { it.chaptersCount }
			.firstOrNull()
			?: error("No premium candidate found from latest endpoint")

		val nonPremiumCandidate = latestEntries
			.filter { !it.latestIsPremium && it.chaptersCount > 3 }
			.sortedBy { it.chaptersCount }
			.firstOrNull()
			?: error("No non-premium candidate found from latest endpoint")

		val premiumManga = findMangaBySlug(parser, premiumCandidate.slug)
			?: error("Premium manga not found in parser listing: ${premiumCandidate.slug}")
		val premiumDetails = parser.getDetails(premiumManga)
		val premiumExpectedAll = fetchChapterEntriesWithPremiumStatus(
			slug = premiumCandidate.slug,
			seedChapterNumberRaw = premiumCandidate.latestNumberRaw,
		)
		val premiumExpectedReadable = takeReadableBeforeFirstPremium(premiumExpectedAll)
		assertParserMatchesExpectedChapters(
			actualManga = premiumDetails,
			expectedReadable = premiumExpectedReadable,
			label = "premium_candidate",
		)
		check(premiumExpectedAll.any { it.isPremium }) {
			"Expected at least one premium chapter for ${premiumCandidate.slug}"
		}
		check(premiumExpectedReadable.size < premiumExpectedAll.size) {
			"Premium boundary was not applied for ${premiumCandidate.slug}"
		}

		val nonPremiumManga = findMangaBySlug(parser, nonPremiumCandidate.slug)
			?: error("Non-premium manga not found in parser listing: ${nonPremiumCandidate.slug}")
		val nonPremiumDetails = parser.getDetails(nonPremiumManga)
		val nonPremiumExpectedAll = fetchChapterEntriesWithPremiumStatus(
			slug = nonPremiumCandidate.slug,
			seedChapterNumberRaw = nonPremiumCandidate.latestNumberRaw,
		)
		val nonPremiumExpectedReadable = takeReadableBeforeFirstPremium(nonPremiumExpectedAll)
		assertParserMatchesExpectedChapters(
			actualManga = nonPremiumDetails,
			expectedReadable = nonPremiumExpectedReadable,
			label = "non_premium_candidate",
		)
		check(nonPremiumExpectedAll.none { it.isPremium }) {
			"Unexpected premium status in non-premium candidate ${nonPremiumCandidate.slug}"
		}

		println(
			"POSEIDON_PREMIUM_CHECK|" +
				"premium_slug=${premiumCandidate.slug}|premium_total=${premiumCandidate.chaptersCount}|" +
				"premium_readable=${premiumDetails.chapters.orEmpty().size}|premium_latest=${premiumCandidate.latestNumberRaw}|" +
				"non_premium_slug=${nonPremiumCandidate.slug}|non_premium_total=${nonPremiumCandidate.chaptersCount}|" +
				"non_premium_readable=${nonPremiumDetails.chapters.orEmpty().size}|non_premium_latest=${nonPremiumCandidate.latestNumberRaw}",
		)
	}

	private suspend fun findMangaBySlug(parser: org.dokiteam.doki.parsers.MangaParser, slug: String): Manga? {
		var offset = 0
		repeat(40) {
			val list = parser.getList(offset = offset, order = SortOrder.UPDATED, filter = MangaListFilter.EMPTY)
			if (list.isEmpty()) return null
			list.firstOrNull { it.url == "/serie/$slug" }?.let { return it }
			offset += list.size
		}
		return null
	}

	private suspend fun fetchLatestEntries(): List<LatestEntry> {
		val payload = requestJson("https://poseidon-scans.co/api/manga/latest?limit=500&page=1")
		val data = payload.optJSONArray("data") ?: return emptyList()
		return buildList {
			for (index in 0 until data.length()) {
				val entry = data.optJSONObject(index) ?: continue
				val slug = entry.optString("slug").trim()
				val title = entry.optString("title").trim()
				val chapters = entry.optJSONArray("chapters")
				val latestChapter = chapters?.optJSONObject(0)
				val latestNumberRaw = normalizeNumberRaw(latestChapter?.opt("number")) ?: continue
				val latestIsPremium = latestChapter?.optBoolean("isPremium", false) ?: false
				val chaptersCount = entry.optJSONObject("_count")?.optInt("chapters", 0) ?: 0
				if (slug.isBlank() || title.isBlank() || chaptersCount <= 0) continue
				add(
					LatestEntry(
						slug = slug,
						title = title,
						latestNumberRaw = latestNumberRaw,
						latestIsPremium = latestIsPremium,
						chaptersCount = chaptersCount,
					),
				)
			}
		}
	}

	private suspend fun fetchChapterEntriesWithPremiumStatus(
		slug: String,
		seedChapterNumberRaw: String,
	): List<ChapterEntry> {
		val payload = requestJson("https://poseidon-scans.co/api/manga/$slug/$seedChapterNumberRaw")
		val chapterList = payload.optJSONObject("data")?.optJSONArray("chapterList") ?: JSONArray()
		val chapterEntries = buildList {
			for (index in 0 until chapterList.length()) {
				val chapter = chapterList.optJSONObject(index) ?: continue
				val numberRaw = normalizeNumberRaw(chapter.opt("number")) ?: continue
				val numberValue = numberRaw.toDoubleOrNull() ?: continue
				add(numberRaw to numberValue)
			}
		}.distinctBy { it.first }.sortedBy { it.second }

			return chapterEntries.map { (numberRaw, numberValue) ->
				val statusPayload = requestJson("https://poseidon-scans.co/api/manga/$slug/$numberRaw")
				val isPremium = statusPayload.optJSONObject("data")
					?.optJSONObject("chapterData")
					?.optBoolean("isPremium", false) ?: false
				ChapterEntry(
					numberRaw = numberRaw,
					numberValue = numberValue,
					isPremium = isPremium,
				)
			}
		}

	private fun takeReadableBeforeFirstPremium(entries: List<ChapterEntry>): List<ChapterEntry> {
		if (entries.isEmpty()) return emptyList()
		val firstPremiumIndex = entries.indexOfFirst { it.isPremium }
		return if (firstPremiumIndex == -1) entries else entries.subList(0, firstPremiumIndex)
	}

	private fun assertParserMatchesExpectedChapters(
		actualManga: Manga,
		expectedReadable: List<ChapterEntry>,
		label: String,
	) {
		val chapters = actualManga.chapters.orEmpty()
		check(chapters.isNotEmpty()) { "No parsed chapters for $label: ${actualManga.publicUrl}" }
		val actualValues = chapters.map { it.number.toDouble() }
		val expectedValues = expectedReadable.map { it.numberValue }

		check(actualValues.size == expectedValues.size) {
			"Chapter count mismatch for $label: parser=${actualValues.size}, expected=${expectedValues.size}"
		}
		check(actualValues.zipWithNext().all { (left, right) -> left <= right }) {
			"Parsed chapter order is not ascending for $label: ${actualManga.publicUrl}"
		}
		actualValues.zip(expectedValues).forEachIndexed { index, (actual, expected) ->
			check(abs(actual - expected) < 0.0001) {
				"Chapter number mismatch at index=$index for $label: parser=$actual expected=$expected"
			}
		}
	}

	private fun normalizeNumberRaw(value: Any?): String? {
		val raw = value?.toString()?.trim()?.takeIf { it.isNotEmpty() && it != "null" } ?: return null
		val numeric = raw.toDoubleOrNull() ?: return raw
		return if (numeric % 1.0 == 0.0) numeric.toInt().toString() else raw
	}

	private suspend fun requestJson(url: String): JSONObject {
		val rawBody = MangaLoaderContextMock.doRequest(url, source).use { response ->
			check(response.isSuccessful) { "Request failed ${response.code}(${response.message}) for $url" }
			response.body?.string().orEmpty()
		}
		return JSONObject(rawBody)
	}
}
