package org.dokiteam.doki.parsers.site.zeistmanga.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@MangaSourceParser("YOKAITEAM", "YokaiTeam", "ar")
internal class YokaiTeam(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.YOKAITEAM, "yokai-team.blogspot.com")
