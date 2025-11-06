package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("VILLAINESSSCAN", "VillainessScan", "pt", ContentType.HENTAI)
internal class VillainessScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.VILLAINESSSCAN, "villainessscan.xyz", pageSize = 10) {
	override val datePattern: String = "dd 'de' MMMM 'de' yyyy"
}
