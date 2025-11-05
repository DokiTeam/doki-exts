package org.dokiteam.doki.parsers.site.madara.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("LIKEMANGANET", "Like-Manga.net", "ar")
internal class LikeMangaNet(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.LIKEMANGANET, "like-manga.net", pageSize = 10)
