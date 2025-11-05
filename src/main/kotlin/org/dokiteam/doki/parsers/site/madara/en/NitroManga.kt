package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("NITROMANGA", "NitroManga", "en")
internal class NitroManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.NITROMANGA, "nitroscans.net", pageSize = 18)
