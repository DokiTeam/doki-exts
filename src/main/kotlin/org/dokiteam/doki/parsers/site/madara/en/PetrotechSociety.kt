package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("PETROTECHSOCIETY", "Petrotech Society", "en", ContentType.HENTAI)
internal class PetrotechSociety(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.PETROTECHSOCIETY, "www.petrotechsociety.org", pageSize = 10) {
	override val postReq = true
}
