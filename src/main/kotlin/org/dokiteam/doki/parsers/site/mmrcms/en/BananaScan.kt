package org.dokiteam.doki.parsers.site.mmrcms.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mmrcms.MmrcmsParser

@MangaSourceParser("BANANASCAN_COM", "BananaScan.Com", "en")
internal class BananaScan(context: MangaLoaderContext) :
	MmrcmsParser(context, MangaParserSource.BANANASCAN_COM, "bananascans.com")
