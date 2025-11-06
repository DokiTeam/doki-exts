package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("YAOIMOBI", "Yaoi.Mobi", "en", ContentType.HENTAI)
internal class YaoiMobi(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.YAOIMOBI, "yaoi.mobi") {
	override val postReq = true
}
