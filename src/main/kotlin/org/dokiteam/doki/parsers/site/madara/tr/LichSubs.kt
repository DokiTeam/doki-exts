package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("LICHSUBS", "LichSubs", "tr")
internal class LichSubs(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LICHSUBS, "www.kuroimanga.com") {
	override val datePattern = "dd/MM/yyyy"
}
