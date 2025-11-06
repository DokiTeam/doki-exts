package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("PRISMA_HENTAI", "PrismaHentai", "pt", ContentType.HENTAI)
internal class Prismahentai(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.PRISMA_HENTAI, "prismahentai.com", 18)
