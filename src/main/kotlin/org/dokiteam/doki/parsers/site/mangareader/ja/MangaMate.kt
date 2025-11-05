package org.dokiteam.doki.parsers.site.mangareader.ja

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@MangaSourceParser("MANGAMATE", "MangaMate", "ja")
internal class MangaMate(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANGAMATE, "manga-mate.org", pageSize = 10, searchPageSize = 10) {
	override val datePattern = "M月 d, yyyy"
	override val sourceLocale: Locale = Locale.ENGLISH
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}
