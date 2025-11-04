package org.dokiteam.doki.parsers.site.madara.th

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("KINGS_MANGA", "SuperDoujin", "th", ContentType.HENTAI)
internal class KingsManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.KINGS_MANGA, "www.superdoujin.org") {
	override val postReq = true
	override val datePattern = "d MMMM yyyy"
}
