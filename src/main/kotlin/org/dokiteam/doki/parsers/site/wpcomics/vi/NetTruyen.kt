package org.dokiteam.doki.parsers.site.wpcomics.vi

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.model.*
import org.dokiteam.doki.parsers.site.wpcomics.WpComicsParser
import org.dokiteam.doki.parsers.util.*
import org.dokiteam.doki.parsers.util.json.getStringOrNull
import java.text.SimpleDateFormat

@MangaSourceParser("NETTRUYEN", "NetTruyen", "vi")
internal class NetTruyen(context: MangaLoaderContext) :
	WpComicsParser(context, MangaParserSource.NETTRUYEN, "nettruyenar.com", 36) {

	override suspend fun getDetails(manga: Manga): Manga = coroutineScope {
		val fullUrl = manga.url.toAbsoluteUrl(domain)
		val docDeferred = async { webClient.httpGet(fullUrl).parseHtml() }
		val chaptersDeferred = async { fetchChapters(manga.url) }
		val tagMap = getOrCreateTagMap()
		val doc = docDeferred.await()
		val tagsElement = doc.select("li.kind p.col-xs-8 a")
		val mangaTags = tagsElement.mapNotNullToSet { tagMap[it.text()] }
		val author = doc.body().select(selectAut).textOrNull()
		manga.copy(
			description = doc.selectFirst(selectDesc)?.html(),
			altTitles = setOfNotNull(doc.selectFirst("h2.other-name")?.textOrNull()),
			authors = setOfNotNull(author),
			state = doc.selectFirst(selectState)?.let {
				when (it.text()) {
					in ongoing -> MangaState.ONGOING
					in finished -> MangaState.FINISHED
					else -> null
				}
			},
			tags = mangaTags,
			rating = doc.selectFirst("div.star input")?.attr("value")?.toFloatOrNull()?.div(5f) ?: RATING_UNKNOWN,
			chapters = chaptersDeferred.await(),
		)
	}

	private suspend fun fetchChapters(mangaUrl: String): List<MangaChapter> {
		val slug = mangaUrl.substringAfterLast('/').substringBeforeLast('-')
		val id = mangaUrl.substringAfterLast('-')
		val chaptersUrl = "/Comic/Services/ComicService.asmx/ChapterList?slug=$slug&comicId=$id".toAbsoluteUrl(domain)
		val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

		val data = webClient.httpGet(chaptersUrl).parseJson().getJSONArray("data")
		return List(data.length()) { i ->
			val jo = data.getJSONObject(data.length() - 1 - i)
			val chapterSlug = jo.getString("chapter_slug")
			val chapterId = jo.getString("chapter_id")
			val chapterUrl = "/truyen-tranh/$slug/$chapterSlug/$chapterId"

			MangaChapter(
				id = generateUid(chapterUrl),
				title = jo.getStringOrNull("chapter_name"),
				number = i + 1f,
				volume = 0,
				url = chapterUrl,
				scanlator = null,
				uploadDate = df.tryParse(jo.getString("updated_at")),
				branch = null,
				source = source,
			)
		}
	}
}
