package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("IZANAMISCANS", "IzanamiScans", "id")
internal class IzanamiScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.IZANAMISCANS, "izanamiscans.my.id", pageSize = 20, searchPageSize = 10)
