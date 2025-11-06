package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken ( "Need refactor")
@MangaSourceParser("BLUESOLO", "BlueSolo", "fr")
internal class BlueSolo(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.BLUESOLO, "www1.bluesolo.org", 10) {
	override val datePattern = "d MMMM yyyy"
}
