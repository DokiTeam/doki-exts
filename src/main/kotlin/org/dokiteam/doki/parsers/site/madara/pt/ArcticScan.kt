package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("ARCTICSCAN", "ArcticScan", "pt")
internal class ArcticScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ARCTICSCAN, "arcticscan.top") {
	override val datePattern: String = "yyyy-MM-dd"
}
