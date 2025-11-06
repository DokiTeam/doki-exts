package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("PARITEHABER", "Paritehaber", "en", ContentType.HENTAI)
internal class Paritehaber(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.PARITEHABER, "www.paritehaber.com", 10)
