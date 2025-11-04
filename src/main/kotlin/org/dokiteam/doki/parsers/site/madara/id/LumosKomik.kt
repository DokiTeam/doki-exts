package org.dokiteam.doki.parsers.site.madara.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import java.util.*

@MangaSourceParser("LUMOSKOMIK", "LumosKomik", "id")
internal class LumosKomik(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LUMOSKOMIK, "lumos01.com") {
	override val tagPrefix = "genre/"
	override val listUrl = "komik/"
	override val datePattern = "dd MMMM yyyy"
	override val sourceLocale: Locale = Locale.ENGLISH
}
