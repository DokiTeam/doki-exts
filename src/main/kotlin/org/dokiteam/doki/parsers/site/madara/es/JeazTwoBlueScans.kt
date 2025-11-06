package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("JEAZTWOBLUESCANS", "Lector HUB", "es")
internal class JeazTwoBlueScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.JEAZTWOBLUESCANS, "lectorhub.j5z.xyz") {
	override val datePattern = "d MMMM, yyyy"
}
