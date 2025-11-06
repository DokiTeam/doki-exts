package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("MANHWALAND", "ManhwaLand.vip", "id", ContentType.HENTAI)
internal class ManhwaLand(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANHWALAND, "www.manhwaland.baby", pageSize = 20, searchPageSize = 10) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
	override val datePattern = "MMM d, yyyy"
}
