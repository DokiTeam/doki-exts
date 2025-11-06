package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("FLOWERMANGA", "FlowerManga", "pt")
internal class FlowerManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.FLOWERMANGA, "flowermanga.net", 24) {
	override val datePattern = "d MMMM yyyy"
}
