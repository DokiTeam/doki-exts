package org.dokiteam.doki.parsers.site.mangareader.es

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("DOUJINS", "Doujins.lat", "es", ContentType.HENTAI)
internal class Doujins(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.DOUJINS, "doujins.lat", pageSize = 20, searchPageSize = 10) {
	override val listUrl = "/comic"
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
