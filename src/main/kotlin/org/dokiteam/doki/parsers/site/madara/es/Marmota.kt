package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MARMOTA", "Marmota", "es", ContentType.COMICS)
internal class Marmota(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MARMOTA, "marmota.me", 48) {
	override val datePattern = "d 'de' MMMMM 'de' yyyy"
	override val tagPrefix = "genero/"
	override val listUrl = "comic/"
}
