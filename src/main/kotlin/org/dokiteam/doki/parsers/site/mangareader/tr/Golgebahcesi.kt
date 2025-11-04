package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("GOLGEBAHCESI", "GolgeBahcesi", "tr")
internal class Golgebahcesi(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.GOLGEBAHCESI, "golgebahcesi.com", pageSize = 14, searchPageSize = 9) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
