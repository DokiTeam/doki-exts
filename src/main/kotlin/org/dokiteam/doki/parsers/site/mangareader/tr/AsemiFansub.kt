package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("ASEMIFANSUB", "AsemiFansub", "tr")
internal class AsemiFansub(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.ASEMIFANSUB, "asemifansub.com", pageSize = 20, searchPageSize = 10)
