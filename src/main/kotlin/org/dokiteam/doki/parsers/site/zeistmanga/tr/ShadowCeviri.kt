package org.dokiteam.doki.parsers.site.zeistmanga.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@MangaSourceParser("SHADOWCEVIRI", "ShadowCeviri", "tr", ContentType.COMICS)
internal class ShadowCeviri(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.SHADOWCEVIRI, "shadowceviri.blogspot.com") {
	override val sateOngoing: String = "Devam Ediyor"
	override val sateFinished: String = "Tamamlandı"
	override val sateAbandoned: String = "Güncel"
}
