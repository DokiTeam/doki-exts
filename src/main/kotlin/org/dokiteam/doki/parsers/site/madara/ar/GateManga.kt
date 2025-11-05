package org.dokiteam.doki.parsers.site.madara.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.Broken

@Broken("Original site closed")
@MangaSourceParser("GATEMANGA", "GateManga", "ar")
internal class GateManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.GATEMANGA, "gatemanga.com") {
	override val postReq = true
	override val datePattern = "d MMMM، yyyy"
	override val listUrl = "ar/"
	override val withoutAjax = true
}
