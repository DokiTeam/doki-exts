package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.Broken

@Broken
@MangaSourceParser("AFRODITSCANS", "AfroditScans", "tr")
internal class AfroditScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.AFRODITSCANS, "afroditscans.com", pageSize = 20, searchPageSize = 10) {
	override val datePattern = "MMM d, yyyy"
	override val isNetShieldProtected = true

	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
