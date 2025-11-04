package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("LEVIATANSCANS", "LsComic", "en")
internal class LeviatanScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LEVIATANSCANS, "lscomic.com", 10)
