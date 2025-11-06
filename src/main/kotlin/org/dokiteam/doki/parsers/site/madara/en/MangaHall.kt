package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANGAHALL", "MangaHolic", "en", ContentType.HENTAI)
internal class MangaHall(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGAHALL, "mangaholic.org", 24)
