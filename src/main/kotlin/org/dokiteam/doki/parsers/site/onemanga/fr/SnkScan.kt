package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser

@MangaSourceParser("SNKSCAN", "SnkScan", "fr")
internal class SnkScan(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.SNKSCAN, "snkscan.com")
