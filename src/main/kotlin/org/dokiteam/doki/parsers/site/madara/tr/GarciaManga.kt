package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("GARCIAMANGA", "GarciaManga", "tr")
internal class GarciaManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.GARCIAMANGA, "garciamanga.com", 20) {
	override val selectPage = "img"
}
