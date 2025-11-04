package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAONLINE_BLOG", "MangaOnline", "pt")
internal class MangaOnline(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAONLINE_BLOG, "mangaonline.blog", 16) {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
