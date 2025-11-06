package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MAIDSECRET", "MaidSecret", "pt")
internal class MaidSecret(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MAIDSECRET, "maidsecret.com", 10) {
	override val datePattern: String = "dd/MM/yyyy"
}
