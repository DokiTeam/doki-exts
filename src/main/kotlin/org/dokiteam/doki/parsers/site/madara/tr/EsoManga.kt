package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("ESOMANGA", "EsoManga", "tr")
internal class EsoManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ESOMANGA, "esomanga.com", 10) {
	override val postReq = true
	override val datePattern = "dd/MM/yyyy"
	override val tagPrefix = "manga-kategoriler/"
}