package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.*
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken("Blocked by Cloudflare")
@MangaSourceParser("EPSILONSOFT", "EpsilonSoft", "fr")
internal class EpsilonSoft(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.EPSILONSOFT, "epsilonsoft.to") {
	override val datePattern = "dd/MM/yy"
	override val withoutAjax = true
}
