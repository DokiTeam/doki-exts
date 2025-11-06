package org.dokiteam.doki.parsers.site.zeistmanga.id

import org.jsoup.nodes.Document
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.MangaTag
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser
import org.dokiteam.doki.parsers.util.*

@MangaSourceParser("TOONCUBUS", "ToonCubus", "id", ContentType.HENTAI)
internal class ToonCubus(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.TOONCUBUS, "www.tooncubus.top") {

	override suspend fun fetchAvailableTags(): Set<MangaTag> {
		val doc = webClient.httpGet("https://$domain/p/genre-list.html").parseHtml()
		return doc.select(".dzdes-genre ul li a").mapToSet {
			MangaTag(
				key = it.attr("href").removeSuffix("/").substringAfterLast("/"),
				title = it.selectFirstOrThrow("span").text(),
				source = source,
			)
		}
	}

	override suspend fun loadChapters(mangaUrl: String, doc: Document): List<MangaChapter> {
		return doc.selectFirstOrThrow("ul.series-chapterlist").select("div.flexch-infoz")
			.mapChapters(reversed = true) { i, div ->
				val url = div.selectFirstOrThrow("a").attr("href")
				val name = div.selectFirstOrThrow("a span").text()
				MangaChapter(
					id = generateUid(url),
					url = url,
					title = name,
					number = i + 1f,
					volume = 0,
					branch = null,
					uploadDate = 0,
					scanlator = null,
					source = source,
				)
			}
	}
}
