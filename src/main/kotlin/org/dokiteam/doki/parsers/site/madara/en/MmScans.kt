package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MMSCANS", "MmScans", "en")
internal class MmScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MMSCANS, "mm-scans.org") {
	override val selectChapter = "li.chapter-li"
	override val selectDesc = "div.summary-text"
	override val withoutAjax = true
}
