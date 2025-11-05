package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser

@MangaSourceParser("HUNTERXHUNTERSCAN", "HunterXHunterScan", "fr")
internal class HunterXHunterScan(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.HUNTERXHUNTERSCAN, "hunterxhunterscan.com")
