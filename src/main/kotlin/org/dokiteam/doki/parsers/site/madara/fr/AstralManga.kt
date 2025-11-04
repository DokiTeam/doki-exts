package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Website, come back soon")
@MangaSourceParser("ASTRALMANGA", "AstralManga", "fr")
internal class AstralManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ASTRALMANGA, "astral-manga.fr") {
	override val datePattern = "dd/MM/yyyy"
}
