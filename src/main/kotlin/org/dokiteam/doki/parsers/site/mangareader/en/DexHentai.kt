package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("DEXHENTAI", "DexHentai", "en", ContentType.HENTAI)
internal class DexHentai(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.DEXHENTAI, "dexhentai.com", 40, 36)
