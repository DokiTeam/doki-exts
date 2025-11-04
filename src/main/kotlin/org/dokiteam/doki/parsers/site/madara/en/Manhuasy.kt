package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANHUASY", "ManhuaSy", "en")
internal class Manhuasy(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANHUASY, "www.manhuasy.com") {
	override val listUrl = "manhua/"
	override val tagPrefix = "manhua-genre/"
}
