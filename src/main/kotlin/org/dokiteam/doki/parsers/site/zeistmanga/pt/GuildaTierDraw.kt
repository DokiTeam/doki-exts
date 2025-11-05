package org.dokiteam.doki.parsers.site.zeistmanga.pt

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.MangaTag
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser
import org.dokiteam.doki.parsers.util.mapToSet
import org.dokiteam.doki.parsers.util.parseHtml
import org.dokiteam.doki.parsers.util.requireElementById

@Broken
@MangaSourceParser("GUILDATIERDRAW", "GuildaTierDraw", "pt")
internal class GuildaTierDraw(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.GUILDATIERDRAW, "www.guildatierdraw.top") {

	override suspend fun fetchAvailableTags(): Set<MangaTag> {
		val doc = webClient.httpGet("https://$domain").parseHtml()
		return doc.requireElementById("LinkList2").select("ul li a").mapToSet {
			MangaTag(
				key = it.attr("href").substringBefore("?").substringAfterLast('/'),
				title = it.text(),
				source = source,
			)
		}
	}
}
