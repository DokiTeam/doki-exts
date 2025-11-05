package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.Broken

@Broken
@MangaSourceParser("SPIDERSCANS", "SpiderScans", "en")
internal class SpiderScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SPIDERSCANS, "spiderscans.xyz", pageSize = 20, searchPageSize = 10)
