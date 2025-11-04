package org.dokiteam.doki.parsers.site.fmreader.ja

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.fmreader.FmreaderParser

@MangaSourceParser("WELOMA", "Weloma", "ja")
internal class Weloma(context: MangaLoaderContext) :
	FmreaderParser(context, MangaParserSource.WELOMA, "weloma.art")
