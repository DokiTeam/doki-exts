package org.dokiteam.doki.parsers.site.mangareader.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken("Blocked by Cloudflare")
@MangaSourceParser("SUSHISCAN", "SushiScan.Net", "fr")
internal class SushiScan(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SUSHISCAN, "sushiscan.net", pageSize = 20, searchPageSize = 10) {
	override val listUrl = "/catalogue"
	override val datePattern = "MMM d, yyyy"
}
