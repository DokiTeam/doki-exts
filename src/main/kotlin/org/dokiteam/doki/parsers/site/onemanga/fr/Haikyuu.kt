package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser

@MangaSourceParser("HAIKYUU", "Haikyuu", "fr")
internal class Haikyuu(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.HAIKYUU, "haikyuu.fr")
