package org.dokiteam.doki.parsers.site.madara.it

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("BEYONDTHEATARAXIA", "Beyond The Ataraxia", "it")
internal class Beyondtheataraxia(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.BEYONDTHEATARAXIA, "www.beyondtheataraxia.com", 10) {
	override val datePattern = "d MMMM yyyy"
}
