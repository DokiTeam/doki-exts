package org.dokiteam.doki.parsers.site.zeistmanga.id

import org.json.JSONObject
import org.jsoup.nodes.Document
import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaPage
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser
import org.dokiteam.doki.parsers.util.*
import org.dokiteam.doki.parsers.util.json.asTypedList
import java.text.SimpleDateFormat

@Broken
@MangaSourceParser("KOMIKGES", "KomikGes", "id")
internal class KomikGes(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.KOMIKGES, "www.komikges.my.id") {

	override suspend fun loadChapters(mangaUrl: String, doc: Document): List<MangaChapter> {
		val feed = doc.selectFirstOrThrow(".episode-list script").html().substringAfter("('").substringBefore("');")
		val url = buildString {
			append("https://")
			append(domain)
			append("/feeds/posts/default/-/")
			append(feed)
			append("?alt=json&orderby=published&max-results=9999")
		}
		val json =
			webClient.httpGet(url).parseJson().getJSONObject("feed").getJSONArray("entry").asTypedList<JSONObject>()
				.reversed()
		val dateFormat = SimpleDateFormat(datePattern, sourceLocale)
		return json.mapIndexedNotNull { i, j ->
			val name = j.getJSONObject("title").getString("\$t")
			val href =
				j.getJSONArray("link").asTypedList<JSONObject>().first { it.getString("rel") == "alternate" }
					.getString("href")
			val dateText = j.getJSONObject("published").getString("\$t").substringBefore("T")
			val slug = mangaUrl.substringAfterLast('/')
			val slugChapter = href.substringAfterLast('/')
			if (slug == slugChapter) {
				return@mapIndexedNotNull null
			}
			MangaChapter(
				id = generateUid(href),
				url = href,
				title = name,
				number = i + 1f,
				volume = 0,
				branch = null,
				uploadDate = dateFormat.parseSafe(dateText),
				scanlator = null,
				source = source,
			)
		}
	}

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val doc = webClient.httpGet(chapter.url.toAbsoluteUrl(domain)).parseHtml()
		return doc.selectFirstOrThrow("script:containsData(let data_content =)").data()
			.split("src\\x3d\\x22").drop(1)
			.map { img ->
				val url = img.substringBefore("\\x22")
				MangaPage(
					id = generateUid(url),
					url = url,
					preview = null,
					source = source,
				)
			}
	}
}
