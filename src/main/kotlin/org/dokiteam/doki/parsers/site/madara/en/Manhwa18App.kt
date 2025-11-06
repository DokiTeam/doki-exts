package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANHWA18APP", "Manhwa18.app", "en", ContentType.HENTAI)
internal class Manhwa18App(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANHWA18APP, "manhwa18.app") {
	override val postReq = true
}
