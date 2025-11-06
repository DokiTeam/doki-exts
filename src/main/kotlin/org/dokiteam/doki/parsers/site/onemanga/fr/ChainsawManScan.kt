package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser

@MangaSourceParser("CHAINSAWMANSCAN", "ChainsawManScan", "fr")
internal class ChainsawManScan(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.CHAINSAWMANSCAN, "chainsawman-scan.com")
