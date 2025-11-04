package org.dokiteam.doki.parsers.site.madara.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import java.util.*

@MangaSourceParser("POJOKMANGA", "PojokManga", "id")
internal class PojokManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.POJOKMANGA, "pojokmanga.info") {
	override val tagPrefix = "komik-genre/"
	override val listUrl = "komik/"
	override val datePattern = "MMM d, yyyy"
	override val sourceLocale: Locale = Locale.ENGLISH
}
