package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.Broken

@Broken("Original site closed")
@MangaSourceParser("DARKNEBULUS", "Darknebulus", "es")
internal class Darknebulus(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.DARKNEBULUS, "www.darknebulus.com")
