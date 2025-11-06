package org.dokiteam.doki.parsers.site.madtheme.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madtheme.MadthemeParser

@MangaSourceParser("TOONILY_ME", "Toonily.Me", "en", ContentType.HENTAI)
internal class ToonilyMe(context: MangaLoaderContext) :
	MadthemeParser(context, MangaParserSource.TOONILY_ME, "toonily.me") {
	override val selectDesc = "div.summary div.section-body p.content"
}
