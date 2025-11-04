package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("PLUMACOMICS", "PlumaComics", "pt")
internal class PlumaComics(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.PLUMACOMICS, "plumacomics.cloud") {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
