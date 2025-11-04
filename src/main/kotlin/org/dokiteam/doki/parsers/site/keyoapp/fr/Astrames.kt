package org.dokiteam.doki.parsers.site.keyoapp.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.keyoapp.KeyoappParser

@Broken
@MangaSourceParser("ASTRAMES", "Astrames", "fr")
internal class Astrames(context: MangaLoaderContext) :
	KeyoappParser(context, MangaParserSource.ASTRAMES, "astrames.fr")
