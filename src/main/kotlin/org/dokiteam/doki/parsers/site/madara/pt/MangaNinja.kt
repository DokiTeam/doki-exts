package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGANINJA", "MangaNinja", "pt")
internal class MangaNinja(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGANINJA, "manganinja.com", 10) {
	override val datePattern: String = "dd/MM/yyyy"
}
