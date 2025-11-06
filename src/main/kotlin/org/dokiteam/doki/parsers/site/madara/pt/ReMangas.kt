package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("REMANGAS", "ReMangas", "pt")
internal class ReMangas(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.REMANGAS, "remangas.net") {
	override val datePattern = "dd/MM/yyyy"
}
