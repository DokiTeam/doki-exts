package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Not dead, changed template")
@MangaSourceParser("KOINOBORISCAN", "KoinoboriScan", "es")
internal class KoinoboriScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.KOINOBORISCAN, "visorkoi.com") {
	override val postReq = true
}
