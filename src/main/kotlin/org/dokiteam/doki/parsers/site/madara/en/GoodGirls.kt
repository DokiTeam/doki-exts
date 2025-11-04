package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("GOODGIRLS", "GoodGirls", "en")
internal class GoodGirls(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.GOODGIRLS, "goodgirls.moe", 10) {
	override val selectDesc = "div.post-content_item:contains(Synopsis) div.summary-content"
}
