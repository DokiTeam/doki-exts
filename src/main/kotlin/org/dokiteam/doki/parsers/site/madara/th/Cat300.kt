package org.dokiteam.doki.parsers.site.madara.th

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("CAT_300", "Cat300", "th", ContentType.HENTAI)
internal class Cat300(context: MangaLoaderContext) : MadaraParser(context, MangaParserSource.CAT_300, "cat-300.com") {
	override val datePattern = "MMMM dd, yyyy"
}
