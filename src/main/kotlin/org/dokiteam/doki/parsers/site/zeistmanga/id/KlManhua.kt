package org.dokiteam.doki.parsers.site.zeistmanga.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.MangaTag
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser
import org.dokiteam.doki.parsers.util.mapToSet
import org.dokiteam.doki.parsers.util.parseHtml
import org.dokiteam.doki.parsers.util.requireElementById

@MangaSourceParser("KLMANHUA", "KlManhua", "id", ContentType.HENTAI)
internal class KlManhua(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.KLMANHUA, "klmanhua.blogspot.com") {

	override suspend fun fetchAvailableTags(): Set<MangaTag> {
		val doc = webClient.httpGet("https://$domain").parseHtml()
		return doc.requireElementById("LinkList1").select("ul li a").mapToSet {
			MangaTag(
				key = it.attr("href").substringBefore("?").substringAfterLast('/'),
				title = it.text(),
				source = source,
			)
		}
	}
}
