package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("ZINMANGA_CC", "ZinManga.cc", "en")
internal class ZinMangaCc(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ZINMANGA_CC, "zinmanga.cc")
