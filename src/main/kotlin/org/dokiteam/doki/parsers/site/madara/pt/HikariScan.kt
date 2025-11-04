package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("HIKARISCAN", "HikariScan", "pt")
internal class HikariScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.HIKARISCAN, "hikariscan.org")
