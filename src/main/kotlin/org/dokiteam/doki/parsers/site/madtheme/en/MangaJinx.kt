package org.dokiteam.doki.parsers.site.madtheme.en

import org.jsoup.nodes.Document
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madtheme.MadthemeParser
import org.dokiteam.doki.parsers.util.*
import java.text.SimpleDateFormat

@MangaSourceParser("MANGAJINX", "MangaJinx", "en")
internal class MangaJinx(context: MangaLoaderContext) :
	MadthemeParser(context, MangaParserSource.MANGAJINX, "mgjinx.com") {
	override val listUrl = "search"

	override suspend fun getChapters(doc: Document): List<MangaChapter> {
		val dateFormat = SimpleDateFormat(datePattern, sourceLocale)
		val id = doc.selectFirstOrThrow("script:containsData(bookId)").data().substringAfter("bookId = ")
			.substringBefore(";")
		val docChapter = webClient.httpGet("https://$domain/service/backend/chaplist/?manga_id=$id").parseHtml()
		return docChapter.select(selectChapter).mapChapters(reversed = true) { i, li ->
			val a = li.selectFirstOrThrow("a")
			val href = a.attrAsRelativeUrl("href")
			val dateText = li.selectFirst(selectDate)?.text()
			MangaChapter(
				id = generateUid(href),
				title = li.selectFirstOrThrow(".chapter-title").text(),
				number = i + 1f,
				volume = 0,
				url = href,
				uploadDate = parseChapterDate(
					dateFormat,
					dateText,
				),
				source = source,
				scanlator = null,
				branch = null,
			)
		}
	}
}
