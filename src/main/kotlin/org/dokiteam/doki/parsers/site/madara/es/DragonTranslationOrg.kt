package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("DRAGONTRANSLATIONORG", "DragonTranslation.org", "es")
internal class DragonTranslationOrg(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.DRAGONTRANSLATIONORG, "dragontranslation.org", 16) {
	override val datePattern = "dd/MM/yyyy"
}
