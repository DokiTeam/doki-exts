package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("LICHMANGAS", "LichMangas", "pt")
internal class LichMangas(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LICHMANGAS, "lichmangas.com", 10) {
	override val datePattern = "dd/MM/yyyy"
}
