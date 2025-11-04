package org.dokiteam.doki.parsers.site.mangareader.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.Broken

@Broken
@MangaSourceParser("MANGAKAZANI", "MangaKazani", "tr")
internal class MangaKazani(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.MANGAKAZANI, "mangakazani.com", pageSize = 19, searchPageSize = 10) {
	override val listUrl = "/seriler"
}
