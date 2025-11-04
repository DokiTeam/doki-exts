package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MOMONOHANASCAN", "MomonohanaScan", "pt")
internal class MomonohanaScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MOMONOHANASCAN, "momonohanascan.com", 10) {
	override val datePattern: String = "dd/MM/yyyy"
}
