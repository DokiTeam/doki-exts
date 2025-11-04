package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANHWARAW_COM", "ManhwaRaw.com", "en", ContentType.HENTAI)
internal class ManhwaRawCom(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANHWARAW_COM, "manhwaraw.com") {
	override val postReq = true
	override val listUrl = "manhwa-raw/"
	override val tagPrefix = "manhwa-raw-genre/"
}
