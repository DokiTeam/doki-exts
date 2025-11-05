package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGA_CRAB", "MangaCrab", "es")
internal class MangaCrab(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGA_CRAB, "mangacrab.org") {
	override val datePattern = "dd/MM/yyyy"
	override val tagPrefix = "manga-genero/"
	override val listUrl = "series/"
	override val selectChapter = "div.listing-chapters_wrap > ul > li"
	override val selectDesc = "div.c-page__content div.modal-contenido p"
	override val selectState = "div.summary-content2"
}
