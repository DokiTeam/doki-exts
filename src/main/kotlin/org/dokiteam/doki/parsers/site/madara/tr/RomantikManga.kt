package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("ROMANTIKMANGA", "RomantikManga", "tr")
internal class RomantikManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ROMANTIKMANGA, "webtoonhatti.club", 20)
