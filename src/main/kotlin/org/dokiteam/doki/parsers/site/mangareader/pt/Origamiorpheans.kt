package org.dokiteam.doki.parsers.site.mangareader.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("ORIGAMIORPHEANS", "Origami Orpheans", "pt")
internal class Origamiorpheans(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.ORIGAMIORPHEANS,
		"origami-orpheans.com",
		pageSize = 20,
		searchPageSize = 10,
	)
