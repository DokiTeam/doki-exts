package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser

@MangaSourceParser("FIREFORCE", "FireForce", "fr")
internal class FireForce(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.FIREFORCE, "fireforce.fr")
