package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANGAOKU", "Mangaoku", "tr")
internal class Mangaoku(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAOKU, "mangaoku.info", 24) {
	override val datePattern = "dd MMMM yyyy"
	override val listUrl = "seri/"
	override val tagPrefix = "tur/"
}
