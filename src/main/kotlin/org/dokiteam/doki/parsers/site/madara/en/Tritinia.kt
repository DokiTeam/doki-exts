package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("TRITINIA", "Tritinia", "en")
internal class Tritinia(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TRITINIA, "tritinia.org")
