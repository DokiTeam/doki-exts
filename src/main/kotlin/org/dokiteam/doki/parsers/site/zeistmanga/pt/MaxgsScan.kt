package org.dokiteam.doki.parsers.site.zeistmanga.pt

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@Broken
@MangaSourceParser("MAXGSSCAN", "MaxgsScan", "pt")
internal class MaxgsScan(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.MAXGSSCAN, "www.maxgsscan.online")
