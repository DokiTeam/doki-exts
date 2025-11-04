package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("ADONISFANSUB", "AdonisFansub", "tr")
internal class AdonisFansub(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.ADONISFANSUB,
		"manga.adonisfansub.com",
		pageSize = 20,
		searchPageSize = 20,
	) {

	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
