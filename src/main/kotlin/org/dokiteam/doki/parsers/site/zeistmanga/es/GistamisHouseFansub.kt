package org.dokiteam.doki.parsers.site.zeistmanga.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.zeistmanga.ZeistMangaParser

@MangaSourceParser("GISTAMISHOUSEFANSUB", "GistamisHouseFansub", "es")
internal class GistamisHouseFansub(context: MangaLoaderContext) :
	ZeistMangaParser(context, MangaParserSource.GISTAMISHOUSEFANSUB, "gistamishousefansub.blogspot.com") {
	override val sateOngoing: String = "Activo"
	override val sateFinished: String = "Completo"
	override val sateAbandoned: String = "Cancelado"
	override val selectPage = ".post img"
}
