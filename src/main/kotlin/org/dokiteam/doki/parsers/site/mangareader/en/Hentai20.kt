package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("HENTAI20", "Hentai20", "en", ContentType.HENTAI)
internal class Hentai20(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.HENTAI20, "hentai20.io", pageSize = 20, searchPageSize = 10)
