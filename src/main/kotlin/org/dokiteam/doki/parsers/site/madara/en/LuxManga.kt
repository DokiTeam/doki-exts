package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("LUXMANGA", "LuxManga", "en")
internal class LuxManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LUXMANGA, "luxmanga.net")
