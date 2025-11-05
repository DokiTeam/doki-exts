package org.dokiteam.doki.parsers.site.mangareader.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.Broken

@Broken // There are no comics on the website at all.
@MangaSourceParser("GREMORYMANGAS", "GremoryMangas", "es")
internal class GremoryMangas(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.GREMORYMANGAS,
		"gremorymangas.com",
		pageSize = 20,
		searchPageSize = 20,
	) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
