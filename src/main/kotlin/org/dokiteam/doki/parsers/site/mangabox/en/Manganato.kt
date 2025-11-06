package org.dokiteam.doki.parsers.site.mangabox.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangabox.MangaboxParser
import org.dokiteam.doki.parsers.Broken

@Broken
@MangaSourceParser("MANGANATO", "Manganato", "en")
internal class Manganato(context: MangaLoaderContext) :
	MangaboxParser(context, MangaParserSource.MANGANATO) {
	override val configKeyDomain = ConfigKey.Domain(
		"natomanga.com",
		"manganato.gg",
	)
	override val otherDomain = "manganato.gg"

	override val authorUrl = "/author/story"
	override val selectPage = ".container-chapter-reader > img"
}
