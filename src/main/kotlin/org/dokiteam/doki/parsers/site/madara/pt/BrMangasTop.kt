package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("BRMANGASTOP", "BrMangasTop", "pt")
internal class BrMangasTop(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.BRMANGASTOP, "brmangas.top", 10) {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
