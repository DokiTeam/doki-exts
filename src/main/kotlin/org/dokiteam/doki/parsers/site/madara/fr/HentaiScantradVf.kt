package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("HENTAISCANTRADVF", "Hentai-Scantrad", "fr", ContentType.HENTAI)
internal class HentaiScantradVf(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.HENTAISCANTRADVF, "hentai.scantrad-vf.cc") {
	override val datePattern = "d MMMM, yyyy"
}
