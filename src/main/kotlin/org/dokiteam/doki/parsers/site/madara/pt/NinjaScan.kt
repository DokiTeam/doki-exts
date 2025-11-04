package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("NINJASCAN", "NinjaComics", "pt")
internal class NinjaScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.NINJASCAN, "ninjacomics.xyz") {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
