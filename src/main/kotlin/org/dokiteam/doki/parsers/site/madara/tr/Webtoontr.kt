package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("WEBTOONTR", "WebtoonTr", "tr")
internal class Webtoontr(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.WEBTOONTR, "webtoontr.net", 16) {
	override val tagPrefix = "webtoon-kategori/"
	override val listUrl = "webtoon/"
	override val datePattern = "dd/MM/yyyy"
}
