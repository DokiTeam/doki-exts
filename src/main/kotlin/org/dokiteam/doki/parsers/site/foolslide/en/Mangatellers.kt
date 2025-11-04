package org.dokiteam.doki.parsers.site.foolslide.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.foolslide.FoolSlideParser

@MangaSourceParser("MANGATELLERS", "Mangatellers", "en")
internal class Mangatellers(context: MangaLoaderContext) :
	FoolSlideParser(context, MangaParserSource.MANGATELLERS, "reader.mangatellers.gr") {
	override val pagination = false
}
