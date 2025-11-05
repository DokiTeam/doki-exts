package org.dokiteam.doki.parsers.site.mangareader.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@Broken
@MangaSourceParser("YDCOMICS", "YdComics", "en")
internal class YdComics(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.YDCOMICS, "yd-comics.com", pageSize = 20, searchPageSize = 10) {
	override val listUrl = "/index.php/series"
}
