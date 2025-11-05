package org.dokiteam.doki.parsers.site.mangareader.th

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("MANGA168", "Manga168", "th", ContentType.HENTAI)
internal class Manga168(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANGA168, "manga168.net", pageSize = 40, searchPageSize = 30) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
