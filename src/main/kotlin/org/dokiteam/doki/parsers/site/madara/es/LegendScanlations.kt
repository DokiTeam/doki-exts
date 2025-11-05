package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("LEGENDSCANLATIONS", "LegendScanlations", "es")
internal class LegendScanlations(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LEGENDSCANLATIONS, "escaneodeleyendas.com", 10) {
	override val datePattern = "dd/MM/yyyy"
}
