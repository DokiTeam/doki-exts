package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Redirect to @MANGAGEZGINI")
@MangaSourceParser("GLORYMANGA", "GloryManga", "tr")
internal class GloryManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.GLORYMANGA, "mangagezgini.site", 18) {
	override val datePattern = "dd/MM/yyyy"
}
