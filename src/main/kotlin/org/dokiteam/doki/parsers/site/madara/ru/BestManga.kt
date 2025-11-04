package org.dokiteam.doki.parsers.site.madara.ru

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("BEST_MANGA", "BestManga", "ru")
internal class BestManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.BEST_MANGA, "bestmanga.club") {
	override val datePattern = "dd.MM.yyyy"
	override val postReq = true
}
