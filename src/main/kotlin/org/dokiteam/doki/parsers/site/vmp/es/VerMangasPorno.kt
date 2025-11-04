package org.dokiteam.doki.parsers.site.vmp.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.vmp.VmpParser

@MangaSourceParser("VERMANGASPORNO", "VerMangasPorno", "es", ContentType.HENTAI)
internal class VerMangasPorno(context: MangaLoaderContext) :
	VmpParser(context, MangaParserSource.VERMANGASPORNO, "vermangasporno.com")
