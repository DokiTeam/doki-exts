package org.dokiteam.doki.parsers.site.madara.ru

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAZAVR", "Mangazavr", "ru", ContentType.HENTAI)
internal class Mangazavr(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAZAVR, "mangazavr.ru") {
	override val listUrl = "/?s=&post_type=wp-manga"
	override val datePattern = "dd.MM.yyyy"
}
