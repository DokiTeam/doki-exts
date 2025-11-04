package org.dokiteam.doki.parsers.site.mangareader.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("SHADOWMANGAS", "ShadowMangas", "es")
internal class Shadowmangas(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SHADOWMANGAS, "shadowmangas.com", pageSize = 10, searchPageSize = 10) {
	override val datePattern = "MMM d, yyyy"
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
