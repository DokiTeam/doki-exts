package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("TIMENAIGHT", "TimeNaight", "tr")
internal class Timenaight(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TIMENAIGHT, "timenaight.org") {
	override val postReq = true
	override val datePattern = "dd/MM/yyyy"
}
