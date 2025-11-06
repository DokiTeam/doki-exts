package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("COPYPASTESCAN", "CopyPasteScan", "es")
internal class Copypastescan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.COPYPASTESCAN, "copypastescan.xyz", 10) {
	override val datePattern = "d MMMM, yyyy"
}
