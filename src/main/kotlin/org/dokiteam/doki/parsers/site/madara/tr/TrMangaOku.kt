package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("TRMANGAOKU", "TrMangaOku", "tr")
internal class TrMangaOku(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TRMANGAOKU, "trmangaoku.com") {
	override val tagPrefix = "tur/"
}
