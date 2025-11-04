package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANHWATOON", "ManhwaToon", "en", ContentType.HENTAI)
internal class ManhwaToon(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANHWATOON, "www.manhwatoon.com")
