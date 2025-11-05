package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("KSGROUPSCANS", "KsGroupScans", "en")
internal class KsGroupScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.KSGROUPSCANS, "ksgroupscans.com")
