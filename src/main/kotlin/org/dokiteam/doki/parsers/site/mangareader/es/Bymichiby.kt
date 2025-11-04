package org.dokiteam.doki.parsers.site.mangareader.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("BYMICHIBY", "Bymichiby", "es", ContentType.HENTAI)
internal class Bymichiby(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.BYMICHIBY, "bymichiby.com", pageSize = 20, searchPageSize = 10)
