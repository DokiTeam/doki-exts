package org.dokiteam.doki.parsers.site.hotcomics.zh

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.hotcomics.HotComicsParser

@MangaSourceParser("TOOMICSSC", "TooMicsSc", "zh")
internal class TooMicsSc(context: MangaLoaderContext) :
	HotComicsParser(context, MangaParserSource.TOOMICSSC, "toomics.com/sc") {
	override val isSearchSupported = false
	override val mangasUrl = "/webtoon/ranking/genre"
	override val selectMangas = "li > div.visual"
	override val selectMangaChapters = "li.normal_ep:has(.coin-type1)"
	override val selectTagsList = "div.genre_list li:not(.on) a"
	override val selectPages = "div[id^=load_image_] img"
	override val onePage = true
}
