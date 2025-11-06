package org.dokiteam.doki.parsers.site.keyoapp.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.keyoapp.KeyoappParser

@MangaSourceParser("EDSCANLATION", "EdScanlation", "fr")
internal class EdScanlation(context: MangaLoaderContext) :
	KeyoappParser(context, MangaParserSource.EDSCANLATION, "edscanlation.fr")
