package org.dokiteam.doki.parsers.site.mangareader.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("RAIKISCAN", "RaikiScan", "es")
internal class Raikiscan(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.RAIKISCAN, "raikiscan.com", pageSize = 20, searchPageSize = 20) {
	override val datePattern = "MMM d, yyyy"
}
