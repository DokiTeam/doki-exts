package org.dokiteam.doki.parsers.site.mangareader.th

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.Broken

@Broken("Original site closed")
@MangaSourceParser("SOMANGA", "SoManga", "th")
internal class SoManga(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SOMANGA, "so-manga.com", pageSize = 5, searchPageSize = 25)
