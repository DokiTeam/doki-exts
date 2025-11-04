package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("SNOWMACHINETRANSLATION", "Snow Machine Translation", "en")
internal class SnowMachineTranslation(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.SNOWMACHINETRANSLATION, "snowmachinetranslation.com", pageSize = 24, searchPageSize = 10) {
	override val listUrl = "/manga"
}