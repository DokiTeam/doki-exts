package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("READERSPOINT", "ReadersPoint", "en")
internal class ReadersPoint(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.READERSPOINT, "qscomics.org", pageSize = 20, searchPageSize = 10) {
	override val listUrl = "/series"
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
