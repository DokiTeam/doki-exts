package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("SHOJOSCANS", "ShojoScans", "en")
internal class ShojoScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SHOJOSCANS, "violetscans.com", pageSize = 20, searchPageSize = 10) {
	override val listUrl = "/comics"
}
