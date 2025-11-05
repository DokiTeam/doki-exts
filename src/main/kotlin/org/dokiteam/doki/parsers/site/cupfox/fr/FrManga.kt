package org.dokiteam.doki.parsers.site.cupfox.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.cupfox.CupFoxParser

@Broken
@MangaSourceParser("FRMANGA", "FrManga", "fr")
internal class FrManga(context: MangaLoaderContext) :
	CupFoxParser(context, MangaParserSource.FRMANGA, "www.frmanga.com")
