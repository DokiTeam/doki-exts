package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("TERRITORIOLEAL", "TerritorioLeal", "es")
internal class TerritorioLeal(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TERRITORIOLEAL, "territorioleal.com") {
	override val datePattern = "d 'de' MMMM 'de' yyyy"
}
