package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("MANHUASCANUS", "ManhuaScan.Us", "en", ContentType.HENTAI)
internal class ManhuaScanUs(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANHUASCANUS, "manhuascan.us", pageSize = 30, searchPageSize = 30) {
	override val datePattern = "dd-MM-yyyy"
	override val listUrl = "/manga-list"
}
