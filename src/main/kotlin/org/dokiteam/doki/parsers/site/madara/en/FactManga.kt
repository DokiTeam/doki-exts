package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("FACTMANGA", "FactManga", "en")
internal class FactManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.FACTMANGA, "factmanga.com")
