package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANGACULTIVATOR", "MangaCultivator", "en")
internal class MangaCultivator(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGACULTIVATOR, "mangacultivator.com", 10)
