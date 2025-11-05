package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("JIMANGA", "S2Manga.io", "en")
internal class JiManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.JIMANGA, "s2manga.io")
