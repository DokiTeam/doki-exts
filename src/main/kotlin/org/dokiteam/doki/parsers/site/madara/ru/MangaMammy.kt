package org.dokiteam.doki.parsers.site.madara.ru

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAMAMMY", "MangaMammy", "ru")
internal class MangaMammy(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAMAMMY, "mangamammy.ru") {
	override val datePattern = "dd.MM.yyyy"
	override val postReq = true
}
