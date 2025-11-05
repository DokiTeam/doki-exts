package org.dokiteam.doki.parsers.site.scan.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.scan.ScanParser

@MangaSourceParser("MANGATERRA", "MangaTerra", "pt")
internal class MangaTerra(context: MangaLoaderContext) :
	ScanParser(context, MangaParserSource.MANGATERRA, "manga-terra.com")
