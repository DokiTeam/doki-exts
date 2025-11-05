package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("IMPERIODABRITANNIA", "ImperioDaBritannia", "pt")
internal class ImperiodaBritannia(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.IMPERIODABRITANNIA, "imperiodabritannia.com", 10) {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
	override val withoutAjax = true
}
