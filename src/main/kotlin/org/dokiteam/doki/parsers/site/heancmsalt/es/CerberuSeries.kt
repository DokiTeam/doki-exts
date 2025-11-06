package org.dokiteam.doki.parsers.site.heancmsalt.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.heancmsalt.HeanCmsAlt
import org.dokiteam.doki.parsers.Broken

@Broken("Not dead, changed template")
@MangaSourceParser("LEGIONSCANS", "CerberusSeries", "es")
internal class CerberuSeries(context: MangaLoaderContext) :
	HeanCmsAlt(context, MangaParserSource.LEGIONSCANS, "legionscans.com")
