package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("FREEMANGATOP", "FreeMangaTop", "en")
internal class FreeMangaTop(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.FREEMANGATOP, "freemangatop.com") {
	override val datePattern = "MM/dd/yyyy"

    override fun getRequestHeaders() = super.getRequestHeaders().newBuilder()
        .add("Referer", "https://$domain/")
        .build()
}
