package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("SCANHENTAIMENU", "ScanHentai.Menu", "fr", ContentType.HENTAI)
internal class ScanHentaiMenu(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.SCANHENTAIMENU, "x-manga.org")
