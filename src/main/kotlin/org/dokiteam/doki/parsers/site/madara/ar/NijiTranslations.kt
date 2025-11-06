package org.dokiteam.doki.parsers.site.madara.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.model.ContentType

@MangaSourceParser("NIJITRANSLATIONS", "Niji Translations", "ar", type = ContentType.HENTAI)
internal class NijiTranslations(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.NIJITRANSLATIONS, "niji-translations.com") {
	override val postReq = true
}
