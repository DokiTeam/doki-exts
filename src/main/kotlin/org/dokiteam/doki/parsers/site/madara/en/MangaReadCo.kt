package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAREADCO", "MangaRead.co", "en")
internal class MangaReadCo(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAREADCO, "mangaread.co", 16) {
	override val tagPrefix = "m-genre/"
	override val datePattern = "yyyy-MM-dd"
}
