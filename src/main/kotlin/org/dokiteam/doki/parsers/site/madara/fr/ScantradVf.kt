package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("SCANTRADVF", "Scantrad-Vf", "fr")
internal class ScantradVf(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.SCANTRADVF, "scantrad-vf.me") {
	override val datePattern = "d MMMM yyyy"
	override val tagPrefix = "genre/"
}
