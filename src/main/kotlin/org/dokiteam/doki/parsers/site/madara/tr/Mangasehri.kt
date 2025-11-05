package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGASEHRI", "MangaSehri.com", "tr")
internal class Mangasehri(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGASEHRI, "manga-sehri.com", 18) {
	override val datePattern = "dd/MM/yyyy"
}
