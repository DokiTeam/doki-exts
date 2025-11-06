package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANHWA18ORG", "Manhwa18.org", "en", ContentType.HENTAI)
internal class Manhwa18Org(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANHWA18ORG, "manhwa18.org") {
	override val postReq = true
}
