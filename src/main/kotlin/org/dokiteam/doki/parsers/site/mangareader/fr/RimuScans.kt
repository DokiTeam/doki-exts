package org.dokiteam.doki.parsers.site.mangareader.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("RIMUSCANS", "RimuScans", "fr")
internal class RimuScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.RIMUSCANS, "rimuscans.com", pageSize = 30, searchPageSize = 10)
