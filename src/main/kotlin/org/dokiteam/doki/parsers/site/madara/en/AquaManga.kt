package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("AQUAMANGA", "AquaManga", "en")
internal class AquaManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.AQUAMANGA, "aquareader.net", 20)
