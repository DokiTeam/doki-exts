package org.dokiteam.doki.parsers.site.zeistmanga.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@MangaSourceParser("MANGASOUL", "MangaSoul", "ar")
internal class MangaSoul(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.MANGASOUL, "www.manga-soul.com")
