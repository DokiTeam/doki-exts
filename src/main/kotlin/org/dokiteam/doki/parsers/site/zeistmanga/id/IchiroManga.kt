package org.dokiteam.doki.parsers.site.zeistmanga.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@MangaSourceParser("ICHIROMANGA", "IchiroManga", "id")
internal class IchiroManga(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.ICHIROMANGA, "ichiromanga.my.id")
