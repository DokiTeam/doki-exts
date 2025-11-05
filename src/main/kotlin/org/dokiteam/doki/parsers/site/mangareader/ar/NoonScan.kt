package org.dokiteam.doki.parsers.site.mangareader.ar

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("NOONSCAN", "NoonScan.com", "ar")
internal class NoonScan(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.NOONSCAN, "noonscan.com", pageSize = 20, searchPageSize = 10)
