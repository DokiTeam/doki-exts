package org.dokiteam.doki.parsers.site.keyoapp.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.keyoapp.KeyoappParser

@MangaSourceParser("KEWNSCANS", "KewnScans", "en")
internal class KewnScans(context: MangaLoaderContext) :
	KeyoappParser(context, MangaParserSource.KEWNSCANS, "kewnscans.org")
