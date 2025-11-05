package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@MangaSourceParser("MANHWAINDOICU", "KomikCinta", "id", ContentType.HENTAI)
internal class ManhwaIndoIcu(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANHWAINDOICU, "komikdewasa.art", pageSize = 30, searchPageSize = 10) {
	override val sourceLocale: Locale = Locale.ENGLISH
	override val listUrl = "/komik"
}
