package org.dokiteam.doki.parsers.site.zeistmanga.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@MangaSourceParser("DUOSCANLATORS", "DuoScanlators", "pt")
internal class DuoScanlators(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.DUOSCANLATORS, "duoscanlators.blogspot.com")
