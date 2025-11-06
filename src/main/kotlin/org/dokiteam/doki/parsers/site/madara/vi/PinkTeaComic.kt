package org.dokiteam.doki.parsers.site.madara.vi

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("PINKTEACOMIC", "PinkTeaComic", "vi")
internal class PinkTeaComic(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.PINKTEACOMIC, "pinkteacomics.com") {
	override val datePattern = "d MMMM, yyyy"
}
