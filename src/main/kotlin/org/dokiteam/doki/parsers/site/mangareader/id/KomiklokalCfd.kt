package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@Broken
@MangaSourceParser("KOMIKLOKALCFD", "KomikLokal.mom", "id", ContentType.HENTAI)
internal class KomiklokalCfd(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.KOMIKLOKALCFD, "komikmu.icu", pageSize = 30, searchPageSize = 10) {
	override val sourceLocale: Locale = Locale.ENGLISH
}