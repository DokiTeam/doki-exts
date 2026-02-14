package org.dokiteam.doki.parsers.site.fr

import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.dokiteam.doki.parsers.MangaLoaderContextMock
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.SortOrder
import java.util.Locale
import kotlin.time.Duration.Companion.minutes

internal class PunkRecordzFullVerificationTest {

	private val source = MangaParserSource.PUNKRECORDZ
	private val apiUrl = "https://api.punkrecordz.com/graphql"

	@Test
	fun verifyPunkRecordzParserEndToEnd() = runTest(timeout = 6.minutes) {
		val parser = MangaLoaderContextMock.newParserInstance(source)

		val updated = parser.getList(offset = 0, order = SortOrder.UPDATED, filter = MangaListFilter.EMPTY)
		check(updated.isNotEmpty()) { "Updated list is empty" }
		val alphabetical = parser.getList(offset = 0, order = SortOrder.ALPHABETICAL, filter = MangaListFilter.EMPTY)
		check(alphabetical.isNotEmpty()) { "Alphabetical list is empty" }
		val relevance = parser.getList(
			offset = 0,
			order = SortOrder.RELEVANCE,
			filter = MangaListFilter(query = "bleach"),
		)
		check(relevance.isNotEmpty()) { "Relevance list is empty for query 'bleach'" }

		val expectedAlpha = alphabetical.sortedBy { it.title.lowercase(Locale.ROOT) }.map { it.id }
		check(alphabetical.map { it.id } == expectedAlpha) { "Alphabetical order is incorrect" }

		val filterOptions = parser.getFilterOptions()
		check(filterOptions.availableTags.isNotEmpty()) { "No tags in filter options" }
		val (tag, taggedList) = filterOptions.availableTags
			.firstNotNullOfOrNull { tag ->
				val list = parser.getList(
					offset = 0,
					order = SortOrder.UPDATED,
					filter = MangaListFilter(tags = setOf(tag)),
				)
				if (list.isEmpty()) null else tag to list
			}
			?: error("No tag with matching mangas found")
		check(taggedList.all { manga -> manga.tags.contains(tag) }) {
			"Tag filter mismatch for '${tag.title}'"
		}

		val detailsCandidates = updated
			.take(20)
			.mapNotNull { manga -> runCatching { parser.getDetails(manga) }.getOrNull() }
		check(detailsCandidates.isNotEmpty()) { "No details candidate loaded from updated list" }
		val detailed = detailsCandidates.maxByOrNull { it.chapters.orEmpty().size }
			?: error("No detailed manga available")

		check(!detailed.coverUrl.isNullOrBlank()) { "Cover URL is blank for ${detailed.publicUrl}" }
		checkImageResponse(detailed.coverUrl)
		check(!detailed.description.isNullOrBlank()) { "Description is blank for ${detailed.publicUrl}" }

		val chapters = detailed.chapters.orEmpty()
		check(chapters.size > 2) { "Too few chapters for ${detailed.publicUrl}: ${chapters.size}" }
		check(chapters.distinctBy { it.id }.size == chapters.size) {
			"Duplicate chapter ids for ${detailed.publicUrl}"
		}
		check(chapters.all { it.url.contains("?id=") }) {
			"Expected chapter URLs with id query parameter for ${detailed.publicUrl}"
		}

		val sampled = buildList<MangaChapter> {
			add(chapters.first())
			chapters.getOrNull(chapters.size / 2)?.let { add(it) }
			add(chapters.last())
		}.distinctBy { it.id }

		val sampledSummary = sampled.map { chapter ->
			val chapterId = chapter.url.substringAfter("id=", "").substringBefore("&")
			check(chapterId.isNotBlank()) { "Missing chapter id in URL ${chapter.url}" }

			val expectedPageCount = fetchPageCountByChapterId(chapterId)
			val pages = parser.getPages(chapter)
			val pageUrls = pages.map { parser.getPageUrl(it) }.distinct()

			check(pageUrls.size == expectedPageCount) {
				"Page count mismatch for ${chapter.url}: parser=${pageUrls.size}, api=$expectedPageCount"
			}
			check(pageUrls.isNotEmpty()) { "No page URL returned for ${chapter.url}" }
			check(pageUrls.all { it.startsWith("https://api.punkrecordz.com/images/") }) {
				"Unexpected page URL format for ${chapter.url}: $pageUrls"
			}
			checkImageResponse(pageUrls.first())
			checkImageResponse(pageUrls.last())
			chapter.url to pageUrls.size
		}

		println(
			"PUNK_FULL_SMOKE|updated=${updated.size}|alpha=${alphabetical.size}|relevance=${relevance.size}|" +
				"manga=${detailed.publicUrl}|chapters=${chapters.size}|tag=${tag.title}|samples=$sampledSummary",
		)
	}

	private suspend fun fetchPageCountByChapterId(chapterId: String): Int {
		val query = """
			query ChapterById(${'$'}id: String!) {
			  chapter(where: { id: ${'$'}id }) {
			    id
			    deleted
			    published
			    manga { id }
			    pages {
			      original
			      colored
			    }
			  }
			}
		""".trimIndent()
		val payload = graphQl(
			query = query,
			variables = JSONObject().put("id", chapterId),
		)
		val chapter = payload.optJSONObject("chapter")
			?: error("Chapter not found in API for id=$chapterId")
		check(!chapter.optBoolean("deleted", false)) { "Chapter $chapterId is deleted" }
		check(chapter.optBoolean("published", true)) { "Chapter $chapterId is unpublished" }
		return chapter.optJSONArray("pages")?.length() ?: 0
	}

	private suspend fun graphQl(query: String, variables: JSONObject = JSONObject()): JSONObject {
		val body = JSONObject()
			.put("query", query)
			.put("variables", variables)
		val raw = MangaLoaderContextMock.httpClient.newCall(
			Request.Builder()
				.url(apiUrl)
				.post(
					body.toString().toRequestBody("application/json; charset=utf-8".toMediaType()),
				)
				.tag(org.dokiteam.doki.parsers.model.MangaSource::class.java, source)
				.build(),
		).execute().use { resp ->
			check(resp.isSuccessful) { "GraphQL request failed ${resp.code}(${resp.message})" }
			resp.body?.string().orEmpty()
		}
		val json = JSONObject(raw)
		val errors = json.optJSONArray("errors")
		check(errors == null || errors.length() == 0) {
			"GraphQL error: ${errors?.optJSONObject(0)?.optString("message")}"
		}
		return json.optJSONObject("data") ?: JSONObject()
	}

	private suspend fun checkImageResponse(url: String?) {
		check(!url.isNullOrBlank()) { "Image URL is blank" }
		MangaLoaderContextMock.doRequest(url, source).use { response ->
			check(response.isSuccessful) { "Image request failed ${response.code}(${response.message}) for $url" }
			val contentType = response.header("Content-Type").orEmpty()
			check(contentType.startsWith("image/")) { "Invalid image Content-Type '$contentType' for $url" }
		}
	}
}
