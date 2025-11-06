package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("TOPREADMANHWA", "TopReadManhwa", "en")
internal class TopReadManhwa(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TOPREADMANHWA, "topreadmanhwa.com") {
	override val datePattern = "MM/dd/yyyy"
}
