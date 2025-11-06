package org.dokiteam.doki.parsers.site.madara.all

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import java.util.*

@MangaSourceParser("MANGACRAZY", "MangaCrazy", "", ContentType.HENTAI)
internal class MangaCrazy(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGACRAZY, "mangacrazy.net") {
	override val sourceLocale: Locale = Locale.ENGLISH
}
