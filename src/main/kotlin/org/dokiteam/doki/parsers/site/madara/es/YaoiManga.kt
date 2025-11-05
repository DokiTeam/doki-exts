package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("YAOIMANGA", "YaoiManga", "es")
internal class YaoiManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.YAOIMANGA, "yaoimanga.es", 42)
