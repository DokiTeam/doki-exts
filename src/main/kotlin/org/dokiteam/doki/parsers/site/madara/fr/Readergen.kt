package org.dokiteam.doki.parsers.site.madara.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("READERGEN", "ReaderGen", "fr")
internal class Readergen(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.READERGEN, "fr.readergen.fr", 18)
