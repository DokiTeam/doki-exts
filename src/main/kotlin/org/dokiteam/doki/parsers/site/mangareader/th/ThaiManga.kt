package org.dokiteam.doki.parsers.site.mangareader.th

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("THAIMANGA", "ThaiManga", "th")
internal class ThaiManga(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.THAIMANGA, "www.thaimanga.net", pageSize = 40, searchPageSize = 10) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
