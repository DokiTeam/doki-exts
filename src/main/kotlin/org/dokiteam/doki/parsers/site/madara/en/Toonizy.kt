package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("TOONIZY", "Toonizy", "en", ContentType.HENTAI)
internal class Toonizy(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TOONIZY, "toonizy.com", 24) {
	override val datePattern = "MMM d, yy"
	override val listUrl = "webtoon/"
}
