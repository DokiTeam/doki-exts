package org.dokiteam.doki.parsers.site.mangareader.ar

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("MANGANOON", "MangaNoon", "ar")
internal class MangaNoon(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANGANOON, "vrnoin.site", pageSize = 24, searchPageSize = 10) {

	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
