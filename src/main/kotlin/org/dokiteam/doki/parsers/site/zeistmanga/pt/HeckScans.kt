package org.dokiteam.doki.parsers.site.zeistmanga.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@MangaSourceParser("HECKSCANS", "HeckScans", "pt")
internal class HeckScans(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.HECKSCANS, "heckscans.blogspot.com")
