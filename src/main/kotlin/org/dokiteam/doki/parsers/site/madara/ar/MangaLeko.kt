package org.dokiteam.doki.parsers.site.madara.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGALEKO", "Manga-Leko.org", "ar")
internal class MangaLeko(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGALEKO, "manga-leko.org", pageSize = 10)
