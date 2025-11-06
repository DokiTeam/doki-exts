package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("S2MANGA", "S2Manga", "en")
internal class S2Manga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.S2MANGA, "s2manga.com") {
	override val datePattern = "MMMM dd, yyyy"
}
