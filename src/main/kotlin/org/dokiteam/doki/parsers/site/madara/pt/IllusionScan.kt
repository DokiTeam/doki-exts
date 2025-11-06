package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("ILLUSIONSCAN", "IllusionScan", "pt", ContentType.HENTAI)
internal class IllusionScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ILLUSIONSCAN, "illusionscan.com") {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
