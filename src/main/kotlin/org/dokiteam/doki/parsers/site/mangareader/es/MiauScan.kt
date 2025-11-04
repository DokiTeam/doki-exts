package org.dokiteam.doki.parsers.site.mangareader.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("MIAUSCAN", "LectorMiau", "es")
internal class MiauScan(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MIAUSCAN, "leemiau.com", pageSize = 20, searchPageSize = 10)
