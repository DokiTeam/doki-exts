package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Not dead, changed template")
@MangaSourceParser("LMTOS", "Lmtos", "es")
internal class Lmtos(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LMTOS, "lmtos.com") {
	override val datePattern = "dd/MM"
}
