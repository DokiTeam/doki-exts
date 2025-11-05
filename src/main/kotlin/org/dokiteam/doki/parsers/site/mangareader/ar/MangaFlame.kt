package org.dokiteam.doki.parsers.site.mangareader.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("MANGAFLAME", "MangaFlame", "ar")
internal class MangaFlame(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANGAFLAME, "mangaflame.org", pageSize = 20, searchPageSize = 10)
