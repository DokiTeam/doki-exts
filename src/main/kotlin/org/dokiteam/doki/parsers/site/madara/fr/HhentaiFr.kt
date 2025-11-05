package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.util.insertCookies

@MangaSourceParser("HHENTAIFR", "H-Hentai", "fr", ContentType.HENTAI)
internal class HhentaiFr(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.HHENTAIFR, "hhentai.fr") {

	init {
		context.cookieJar.insertCookies(
			domain,
			"age_gate=32;",
		)
	}
}
