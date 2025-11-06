package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANHUAUS", "ManhuaUs", "en")
internal class Manhuaus(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANHUAUS, "manhuaus.com")
