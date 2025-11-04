package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("TREE_MANGA", "TreeManga", "en")
internal class TreeManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TREE_MANGA, "treemanga.com") {
	override val datePattern = "MM/dd/yyyy"
}
