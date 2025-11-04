package org.dokiteam.doki.parsers.site.mangareader.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.Broken

@Broken("Original site closed")
@MangaSourceParser("DISKUSSCAN", "DiskusScan", "pt")
internal class DiskusScan(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.DISKUSSCAN, "diskusscan.online", pageSize = 20, searchPageSize = 10)
