package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Not dead, changed template")
@MangaSourceParser("CERISE_SCANS", "CeriseScans", "pt")
internal class CeriseScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.CERISE_SCANS, "cerise.leitorweb.com") {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
