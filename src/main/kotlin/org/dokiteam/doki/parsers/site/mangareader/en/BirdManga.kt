package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("BIRDMANGA", "BirdManga", "en")
internal class BirdManga(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.BIRDMANGA, "birdmanga.com", pageSize = 20, searchPageSize = 10) {
	override val encodedSrc = true

	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
