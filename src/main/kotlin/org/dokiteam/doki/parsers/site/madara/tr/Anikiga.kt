package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("ANIKIGA", "Anikiga", "tr")
internal class Anikiga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ANIKIGA, "anikiga.com") {
	override val tagPrefix = "manga-tur/"
	override val datePattern = "d MMMM yyyy"
	override val postReq = true
}
