package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("KABUSMANGA", "KabusManga", "tr")
internal class KabusManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.KABUSMANGA, "kabusmanga.com") {
	override val datePattern = "dd/MM/yyyy"
}
