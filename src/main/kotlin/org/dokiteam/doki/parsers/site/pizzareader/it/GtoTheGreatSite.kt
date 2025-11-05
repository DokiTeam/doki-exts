package org.dokiteam.doki.parsers.site.pizzareader.it

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.pizzareader.PizzaReaderParser

@MangaSourceParser("GTOTHEGREATSITE", "GtoTheGreatSite", "it")
internal class GtoTheGreatSite(context: MangaLoaderContext) :
	PizzaReaderParser(context, MangaParserSource.GTOTHEGREATSITE, "reader.gtothegreatsite.net")
