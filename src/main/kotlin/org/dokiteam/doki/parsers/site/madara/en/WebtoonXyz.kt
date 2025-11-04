package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("WEBTOONXYZ", "Webtoon.xyz", "en", ContentType.HENTAI)
internal class WebtoonXyz(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.WEBTOONXYZ, "www.webtoon.xyz", 20) {
	override val tagPrefix = "webtoon-genre/"
	override val listUrl = "read/"
	override val datePattern = "d MMM yyyy"
}
