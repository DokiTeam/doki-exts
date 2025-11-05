package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("LERHENTAI", "LerHentai", "pt", ContentType.HENTAI)
internal class LerHentai(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LERHENTAI, "lerhentai.com", 20) {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
