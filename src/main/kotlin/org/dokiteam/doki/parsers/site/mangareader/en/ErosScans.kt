package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("EROSSCANS", "ErosScans", "en")
internal class ErosScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.EROSSCANS, "erosxscans.xyz", pageSize = 20, searchPageSize = 10)
