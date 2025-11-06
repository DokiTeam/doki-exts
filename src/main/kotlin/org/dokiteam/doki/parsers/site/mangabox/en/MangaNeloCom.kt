package org.dokiteam.doki.parsers.site.mangabox.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangabox.MangaboxParser

@MangaSourceParser("MANGANELO_COM", "MangaNelo.com", "en")
internal class MangaNeloCom(context: MangaLoaderContext) :
	MangaboxParser(context, MangaParserSource.MANGANELO_COM) {
	override val configKeyDomain = ConfigKey.Domain("nelomanga.com", "m.manganelo.com", "chapmanganelo.com")
	override val otherDomain = "chapmanganelo.com"
}
