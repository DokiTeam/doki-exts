package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MUGIWARASOFICIAL", "MugiwarasOficial", "pt")
internal class MugiwarasOficial(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MUGIWARASOFICIAL, "mugiwarasoficial.com") {
	override val datePattern: String = "dd/MM/yyyy"
}
