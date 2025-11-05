package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("SHIJIESCANS", "ShijieScans", "tr")
internal class ShijieScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SHIJIESCANS, "shijiescans.com", pageSize = 20, searchPageSize = 10) {
	override val listUrl = "/seri"
}
