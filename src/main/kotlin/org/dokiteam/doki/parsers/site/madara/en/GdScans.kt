package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("GDSCANS", "GdScans", "en")
internal class GdScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.GDSCANS, "gdscans.com", 10) {
	override val tagPrefix = "webtoon-genre/"
}
