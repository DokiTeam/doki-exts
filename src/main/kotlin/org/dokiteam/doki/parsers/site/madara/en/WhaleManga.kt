package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("WHALEMANGA", "WhaleManga", "en")
internal class WhaleManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.WHALEMANGA, "whalemanga.com", 10)
