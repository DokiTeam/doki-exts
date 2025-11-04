package org.dokiteam.doki.parsers.site.madara.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("CROWSCANS", "Hadess", "ar")
internal class CrowScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.CROWSCANS, "www.hadess.xyz") {
	override val datePattern = "dd MMMM، yyyy"
}
