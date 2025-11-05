package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("CULTUREDWORKS", "CulturedWorks", "en")
internal class CulturedWorks(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.CULTUREDWORKS,
		"culturedworks.com",
		pageSize = 20,
		searchPageSize = 10,
	) {
	override val selectMangaList = ".listupd .bs .bsx"
}
