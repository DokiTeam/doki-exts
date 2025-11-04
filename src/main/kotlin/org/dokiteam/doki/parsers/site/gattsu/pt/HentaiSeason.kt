package org.dokiteam.doki.parsers.site.gattsu.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.gattsu.GattsuParser

@MangaSourceParser("HENTAISEASON", "HentaiSeason", "pt", ContentType.HENTAI)
internal class HentaiSeason(context: MangaLoaderContext) :
	GattsuParser(context, MangaParserSource.HENTAISEASON, "hentaiseason.com")
