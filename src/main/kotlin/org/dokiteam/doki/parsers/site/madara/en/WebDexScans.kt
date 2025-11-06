package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("WEBDEXSCANS", "WebDexScans", "en")
internal class WebDexScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.WEBDEXSCANS, "webdexscans.com")
