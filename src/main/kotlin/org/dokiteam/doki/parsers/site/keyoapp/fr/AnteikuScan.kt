package org.dokiteam.doki.parsers.site.keyoapp.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.keyoapp.KeyoappParser

@Broken
@MangaSourceParser("ANTEIKUSCAN", "AnteikuScan", "fr")
internal class AnteikuScan(context: MangaLoaderContext) :
	KeyoappParser(context, MangaParserSource.ANTEIKUSCAN, "anteikuscan.fr")
