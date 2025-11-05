package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAWEEBS", "MangaWeebs", "en")
internal class MangaWeebs(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAWEEBS, "mangaweebs.in", pageSize = 20) {
	override val datePattern = "dd MMMM HH:mm"
}
