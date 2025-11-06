package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("NVMANGA", "NvManga", "en")
internal class NvManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.NVMANGA, "1manhwa.com") {
	override val datePattern = "dd/MM/yyyy"
	override val tagPrefix = "genre/"
	override val listUrl = "webtoon/"
}
