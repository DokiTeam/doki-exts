package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("LUNAR_SCAN", "LunarScan.org", "en", ContentType.HENTAI)
internal class LunarScan(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.LUNAR_SCAN, "lunarscan.org", pageSize = 20, searchPageSize = 20) {
	override val listUrl = "/series"
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
