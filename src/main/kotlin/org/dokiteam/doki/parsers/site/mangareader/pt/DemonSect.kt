package org.dokiteam.doki.parsers.site.mangareader.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("DEMONSECT", "DemonSect", "pt")
internal class DemonSect(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.DEMONSECT, "seitacelestial.com", pageSize = 20, searchPageSize = 10) {
	override val listUrl = "/comics"

	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
