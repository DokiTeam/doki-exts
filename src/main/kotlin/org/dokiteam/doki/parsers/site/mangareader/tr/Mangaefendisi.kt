package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("MANGAEFENDISI", "MangaEfendisi", "tr")
internal class Mangaefendisi(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.MANGAEFENDISI,
		"mangaefendisi.net",
		pageSize = 30,
		searchPageSize = 20,
	) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
