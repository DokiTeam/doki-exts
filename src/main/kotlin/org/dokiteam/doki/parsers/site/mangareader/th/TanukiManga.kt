package org.dokiteam.doki.parsers.site.mangareader.th

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("TANUKIMANGA", "TanukiManga", "th")
internal class TanukiManga(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.TANUKIMANGA,
		"www.tanuki-manga.com",
		pageSize = 40,
		searchPageSize = 10,
	) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
