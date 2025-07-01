package org.dokiteam.doki.parsers.site.madara.vi

import org.jsoup.nodes.Document
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.util.*

@MangaSourceParser("TRUYENTRANHDAMMYY", "Truyện Tranh Đam Mỹ", "vi")
internal class TruyenTranhDamMyy(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TRUYENTRANHDAMMYY, "truyentranhdammyy.site") {
	override val postReq = true
	override suspend fun loadChapters(mangaUrl: String, document: Document): List<MangaChapter> {
		val mangaId = document.select("div#manga-chapters-holder").attr("data-id")
		val url = "https://$domain/wp-admin/admin-ajax.php"
		val postData = "action=manga_get_chapters&manga=$mangaId"
		val doc = webClient.httpPost(url, postData).parseHtml()
		return doc.select(selectChapter).mapChapters(reversed = true) { i, li ->
			val a = li.selectFirst("a")
			val href = a?.attrAsRelativeUrlOrNull("href") ?: li.parseFailed("Link is missing")
			val link = href + stylePage
			val name = a.selectFirst("p")?.text() ?: a.ownText()
			MangaChapter(
				id = generateUid(href),
				url = link,
				title = name,
				number = i + 1f,
				volume = 0,
				branch = null,
				uploadDate = 0, // Correct datePattern not found.
				scanlator = null,
				source = source,
			)
		}
	}
}
