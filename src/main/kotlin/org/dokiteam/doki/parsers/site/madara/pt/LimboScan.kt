package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("LIMBOSCAN", "LimboScan", "pt")
internal class LimboScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LIMBOSCAN, "limboscan.com.br") {
	override val tagPrefix = "obras-genre/"
	override val listUrl = "obras/"
	override val datePattern: String = "dd/MM/yyyy"
}
