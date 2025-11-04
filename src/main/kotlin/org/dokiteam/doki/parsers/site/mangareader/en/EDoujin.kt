package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("EDOUJIN", "EHentaiManga", "en", ContentType.HENTAI)
internal class EDoujin(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.EDOUJIN, "ehentaimanga.com", pageSize = 25, searchPageSize = 10)
