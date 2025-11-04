package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("ONLYMANHWA", "OnlyManhwa", "en")
internal class OnlyManhwa(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ONLYMANHWA, "onlymanhwa.org") {
	override val listUrl = "manhwa/"
	override val datePattern = "d 'de' MMMM 'de' yyyy"
}
