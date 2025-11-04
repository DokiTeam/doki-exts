package org.dokiteam.doki.parsers.site.mangareader.id

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import java.util.*

@Broken
@MangaSourceParser("MANHWAPLUS", "ManhwaPlus", "id", ContentType.HENTAI)
internal class ManhwaPlus(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANHWAPLUS, "manhwablue.com", 20, 10) {
	override val sourceLocale: Locale = Locale.ENGLISH
	override val listUrl = "/komik"
	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)
}