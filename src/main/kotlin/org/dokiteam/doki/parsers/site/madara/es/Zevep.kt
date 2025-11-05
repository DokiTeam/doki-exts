package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.Broken

@Broken
@MangaSourceParser("ZEVEP", "Zevep", "es")
internal class Zevep(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ZEVEP, "zevep.com", 16)
