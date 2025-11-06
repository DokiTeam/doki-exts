package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("ZIN_MANGA_COM", "Zin-Manga.com", "en")
internal class ZinMangaCom(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ZIN_MANGA_COM, "zin-manga.com") {
	override val selectPage = "img"
}
