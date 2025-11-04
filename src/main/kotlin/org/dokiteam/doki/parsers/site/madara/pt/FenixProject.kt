package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("FENIXPROJECT", "FenixProject", "pt")
internal class FenixProject(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.FENIXPROJECT, "fenixproject.site", 10) {
	override val datePattern: String = "dd/MM/yyyy"
}
