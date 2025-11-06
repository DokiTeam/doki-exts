package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("LEITORDEMANGA", "LeitorDeManga", "pt")
internal class LeitorDeManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LEITORDEMANGA, "leitordemanga.com", 10) {
	override val datePattern = "dd/MM/yyyy"
	override val listUrl = "ler-manga/"
}
