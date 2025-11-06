package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANGA1001", "Manga1001", "en")
internal class Manga1001(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGA1001, "manga-1001.com", 10)
