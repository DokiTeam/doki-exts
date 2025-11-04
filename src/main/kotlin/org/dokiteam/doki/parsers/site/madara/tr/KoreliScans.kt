package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("KORELISCANS", "KoreliScans", "tr")
internal class KoreliScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.KORELISCANS, "koreliscans.com", 10) {
	override val datePattern = "d MMMM"
}
