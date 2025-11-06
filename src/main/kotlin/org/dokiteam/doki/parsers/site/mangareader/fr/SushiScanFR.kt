package org.dokiteam.doki.parsers.site.mangareader.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("SUSHISCANFR", "SushiScan.fr", "fr")
internal class SushiScanFR(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SUSHISCANFR, "sushiscan.fr", pageSize = 36, searchPageSize = 10) {
	override val listUrl = "/catalogue"
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
