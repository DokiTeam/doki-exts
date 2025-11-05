package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANHWA68", "Manhwa68", "en", ContentType.HENTAI)
internal class Manhwa68(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANHWA68, "manhwa68.com")
