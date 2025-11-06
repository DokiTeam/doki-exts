package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANGAOKUSANA", "MangaOkusana", "tr")
internal class MangaOkusana(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAOKUSANA, "mangaokusana.com") {
	override val datePattern = "dd MMMM yyyy"
}
