package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@MangaSourceParser("NGOMIK", "Ngomik", "id")
internal class Ngomik(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.NGOMIK, "ngomik.mom", pageSize = 20, searchPageSize = 5) {
	override val sourceLocale: Locale = Locale.ENGLISH
}
