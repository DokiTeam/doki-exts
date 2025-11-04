package org.dokiteam.doki.parsers.site.madara.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("OLAOE", "Olaoe", "ar")
internal class Olaoe(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.OLAOE, "olaoe.cyou") {
	override val datePattern = "dd-MM-yyyy"
	override val tagPrefix = "/شوجو"
	override val listUrl = "works/"
}
