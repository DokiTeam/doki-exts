package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("RAINDROPTEAMFAN", "Raindrop Fansub", "tr")
internal class Raindropteamfan(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.RAINDROPTEAMFAN,
		"www.raindropteamfan.com",
		pageSize = 25,
		searchPageSize = 10,
	) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}

