package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser
import org.dokiteam.doki.parsers.Broken

@Broken("Original site closed")
@MangaSourceParser("CENTURYBOYS20TH", "20ThCenturyBoys", "fr")
internal class CenturyBoys20Th(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.CENTURYBOYS20TH, "20thcenturyboys.fr")
