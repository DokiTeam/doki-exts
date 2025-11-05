package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("KOMIKMAMA", "KomikMama", "id")
internal class KomikMama(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.KOMIKMAMA, "komikmama.lat", pageSize = 30, searchPageSize = 10) {
	override val listUrl = "/komik"
}
