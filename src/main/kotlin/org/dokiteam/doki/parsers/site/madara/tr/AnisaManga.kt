package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("ANISA_MANGA", "AnisaManga", "tr")
internal class AnisaManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ANISA_MANGA, "anisamanga.com")
