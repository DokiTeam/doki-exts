package org.dokiteam.doki.parsers.site.zeistmanga.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser
import org.dokiteam.doki.parsers.Broken

@Broken
@MangaSourceParser("NEKOSCANS", "NekoScans", "es")
internal class NekoScans(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.NEKOSCANS, "www.nekoscans.org")
