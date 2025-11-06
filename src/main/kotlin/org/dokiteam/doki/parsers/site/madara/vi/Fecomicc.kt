package org.dokiteam.doki.parsers.site.madara.vi

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.Broken

@Broken("No longer available")
@MangaSourceParser("FECOMICC", "Fecomic", "vi")
internal class Fecomicc(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.FECOMICC, "mangasup.net", 9) {
	override val listUrl = "comic/"
	override val tagPrefix = "the-loai/"
	override val datePattern = "dd/MM/yyyy"
}
