package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("ATEMPORAL", "Atemporal", "pt")
internal class Atemporal(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ATEMPORAL, "atemporal.cloud") {
	override val datePattern: String = "d 'de' MMMM 'de' yyyy"
	override val withoutAjax = true
}
