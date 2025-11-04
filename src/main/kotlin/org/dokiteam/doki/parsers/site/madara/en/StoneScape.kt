package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("STONESCAPE", "StoneScape", "en")
internal class StoneScape(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.STONESCAPE, "stonescape.xyz", 10) {
	override val listUrl = "series/"
	override val tagPrefix = "series-genre/"
}
