package org.dokiteam.doki.parsers.site.mangareader.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("STELLARSABER", "StellarSaber", "ar")
internal class StellarSaber(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaParserSource.STELLARSABER, "stellarsaber.pro", pageSize = 32, searchPageSize = 10)
