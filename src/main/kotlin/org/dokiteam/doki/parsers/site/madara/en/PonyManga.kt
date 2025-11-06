package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("PONYMANGA", "PonyManga", "en", ContentType.HENTAI)
internal class PonyManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.PONYMANGA, "ponymanga.com", 10)
