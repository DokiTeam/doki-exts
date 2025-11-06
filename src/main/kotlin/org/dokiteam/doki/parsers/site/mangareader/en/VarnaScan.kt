package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("VARNASCAN", "VarnaScan", "en")
internal class VarnaScan(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.VARNASCAN, "varnascan.xyz", pageSize = 20, searchPageSize = 10)
