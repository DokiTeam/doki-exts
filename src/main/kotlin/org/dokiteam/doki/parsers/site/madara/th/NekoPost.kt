package org.dokiteam.doki.parsers.site.madara.th

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Redirect to @KINGS_MANGA")
@MangaSourceParser("NEKOPOST", "NekoPost", "th", ContentType.HENTAI)
internal class NekoPost(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.NEKOPOST, "www.superdoujin.org") {
	override val postReq = true
	override val datePattern = "d MMMM yyyy"
}
