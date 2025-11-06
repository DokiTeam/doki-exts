package org.dokiteam.doki.parsers.site.mangareader.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("PORNHWASCANS", "PornhwaScans", "fr")
internal class PornhwaScans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.PORNHWASCANS, "pornhwascans.fr", pageSize = 24, searchPageSize = 10) {
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
	override val selectChapter = "div.chapter-list > a.chapter-item"

}
