package org.dokiteam.doki.parsers.site.guya.all

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.guya.GuyaParser

@MangaSourceParser("MAHOUSHOUJOBU", "MahouShoujobu")
internal class MahouShoujobu(context: MangaLoaderContext) :
	GuyaParser(context, MangaParserSource.MAHOUSHOUJOBU, "mahoushoujobu.com")
