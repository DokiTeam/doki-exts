package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
//This source requires an account.
@MangaSourceParser("GRIMELEK", "Grimelek", "tr")
internal class Grimelek(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.GRIMELEK, "siyahmelek.org", 20) {
	override val datePattern = "d MMMM yyyy"
	override val listUrl = "seri/"
}
