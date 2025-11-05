package org.dokiteam.doki.parsers.site.mangaworld.it

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangaworld.MangaWorldParser

@MangaSourceParser("MANGAWORLD", "MangaWorld", "it")
internal class MangaWorld(
	context: MangaLoaderContext,
) : MangaWorldParser(context, MangaParserSource.MANGAWORLD, "mangaworld.ac")
