package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAROCK", "MangaRock", "en")
internal class MangaRock(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAROCK, "mangarockteam.com") {
	override val datePattern = "MMMM dd, yyyy"
}
