package org.dokiteam.doki.parsers.site.mangareader.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("ASIALOTUSS", "AsiaLotuss", "es")
internal class AsiaLotuss(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.ASIALOTUSS, "asialotuss.com", pageSize = 20, searchPageSize = 10)
