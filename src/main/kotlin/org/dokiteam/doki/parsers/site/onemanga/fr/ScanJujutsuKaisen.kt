package org.dokiteam.doki.parsers.site.onemanga.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.onemanga.OneMangaParser

@MangaSourceParser("SCANJUJUTSUKAISEN", "ScanJujutsuKaisen", "fr")
internal class ScanJujutsuKaisen(context: MangaLoaderContext) :
	OneMangaParser(context, MangaParserSource.SCANJUJUTSUKAISEN, "scanjujutsukaisen.com")
