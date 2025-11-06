package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("YUMEKOMIK", "YumeKomik", "id")
internal class YumeKomik(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.YUMEKOMIK, "yumekomik.com", pageSize = 20, searchPageSize = 10)
