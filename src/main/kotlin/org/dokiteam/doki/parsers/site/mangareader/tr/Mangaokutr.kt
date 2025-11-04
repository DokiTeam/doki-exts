package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("MANGAOKUTR", "MangaOkuTr", "tr")
internal class Mangaokutr(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANGAOKUTR, "mangaokutr.com", pageSize = 25, searchPageSize = 20)
