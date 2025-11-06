package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser

@MangaSourceParser("KINGDOMSCAN", "KingdomScan", "fr")
internal class KingdomScan(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.KINGDOMSCAN, "kingdomscan.com")
