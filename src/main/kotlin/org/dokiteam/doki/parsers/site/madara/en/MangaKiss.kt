package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAKISS", "MangaKiss", "en")
internal class MangaKiss(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAKISS, "mangakiss.org", 10)
