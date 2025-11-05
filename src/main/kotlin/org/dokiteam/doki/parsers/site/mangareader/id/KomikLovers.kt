package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.Broken
import java.util.*

@Broken // The site's servers are not responding; it may be closed.
@MangaSourceParser("KOMIKLOVERS", "KomikLovers", "id")
internal class KomikLovers(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.KOMIKLOVERS, "komiklovers.com", pageSize = 20, searchPageSize = 10) {
	override val sourceLocale: Locale = Locale.ENGLISH
	override val listUrl = "/komik"
}
