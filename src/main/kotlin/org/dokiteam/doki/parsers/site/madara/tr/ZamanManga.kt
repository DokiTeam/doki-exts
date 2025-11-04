package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("ZAMANMANGA", "ZamanManga", "tr")
internal class ZamanManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ZAMANMANGA, "zamanmanga.com") {
	override val datePattern = "dd MMMM yyyy"
}