package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Redirect to @MANGAREAD")
@MangaSourceParser("MANGAEFFECT", "MangaEffect", "en")
internal class MangaEffect(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAEFFECT, "www.mangaread.org") {
	override val datePattern = "dd.MM.yyyy"
	override val withoutAjax = true
}
