package org.dokiteam.doki.parsers.site.madara.id

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("HWAGO", "Hwago", "id")
internal class Hwago(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.HWAGO, "hwago01.xyz") {
	override val datePattern = "d MMMM yyyy"
}
