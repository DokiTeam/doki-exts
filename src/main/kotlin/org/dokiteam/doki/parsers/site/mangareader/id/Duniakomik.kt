package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("DUNIAKOMIK", "DuniaKomik", "id", ContentType.HENTAI)
internal class Duniakomik(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.DUNIAKOMIK, "duniakomik.org", pageSize = 12, searchPageSize = 12) {
	override val datePattern = "MMM d, yyyy"
}