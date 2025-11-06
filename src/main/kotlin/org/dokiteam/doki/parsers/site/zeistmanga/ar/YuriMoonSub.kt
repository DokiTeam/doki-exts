package org.dokiteam.doki.parsers.site.zeistmanga.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser
import org.dokiteam.doki.parsers.model.ContentType

@MangaSourceParser("YURIMOONSUB", "Yurimoonsub.blogspot.com", "ar", type = ContentType.HENTAI)
internal class YuriMoonSub(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.YURIMOONSUB, "yurimoonsub.blogspot.com")
