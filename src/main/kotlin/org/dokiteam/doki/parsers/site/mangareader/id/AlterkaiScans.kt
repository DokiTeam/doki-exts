package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@MangaSourceParser("ALTERKAISCANS", "AlterkaiScans", "id")
internal class AlterkaiScans(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.ALTERKAISCANS,
		"alterkaiscans.my.id",
		pageSize = 20,
		searchPageSize = 10,
	) {
	override val sourceLocale: Locale = Locale.ENGLISH
}
