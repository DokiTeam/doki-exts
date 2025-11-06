package org.dokiteam.doki.parsers.site.madtheme.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madtheme.MadthemeParser

@MangaSourceParser("TOONITUBE", "TooniTube", "en", ContentType.HENTAI)
internal class TooniTube(context: MangaLoaderContext) :
	MadthemeParser(context, MangaParserSource.TOONITUBE, "toonitube.com") {
	override val selectDesc = "div.summary div.section-body p.content"
}
