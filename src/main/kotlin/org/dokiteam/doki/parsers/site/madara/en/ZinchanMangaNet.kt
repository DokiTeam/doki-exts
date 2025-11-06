package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("ZINCHANMANGA_NET", "ZinchanManga.net", "en")
internal class ZinchanMangaNet(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ZINCHANMANGA_NET, "zinchangmanga.net", 10)
