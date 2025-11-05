package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@MangaSourceParser("MANGASUSUKU", "MangaSusuku", "id", ContentType.HENTAI)
internal class MangaSusuku(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANGASUSUKU, "mangasusuku.com", pageSize = 20, searchPageSize = 20) {
	override val listUrl = "/komik"
	override val datePattern = "MMM d, yyyy"
	override val sourceLocale: Locale = Locale.ENGLISH
	override val isNetShieldProtected = true
}
