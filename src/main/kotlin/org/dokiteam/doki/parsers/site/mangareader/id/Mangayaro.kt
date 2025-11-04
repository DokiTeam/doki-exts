package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@MangaSourceParser("MANGAYARO", "MangaYaro", "id")
internal class Mangayaro(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.MANGAYARO,
		"mangayaro.id",
		pageSize = 20,
		searchPageSize = 20,
	) {
	override val sourceLocale: Locale = Locale.ENGLISH
}
