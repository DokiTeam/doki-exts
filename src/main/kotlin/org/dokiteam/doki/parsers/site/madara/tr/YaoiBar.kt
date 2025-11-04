package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("YAOIBAR", "YaoiBar", "tr")
internal class YaoiBar(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.YAOIBAR, "yaoibar.gay", 16) {
	override val datePattern = "dd/MM/yyyy"
}
