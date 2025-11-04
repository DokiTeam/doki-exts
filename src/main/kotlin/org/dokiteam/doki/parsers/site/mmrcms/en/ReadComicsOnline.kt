package org.dokiteam.doki.parsers.site.mmrcms.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mmrcms.MmrcmsParser

@MangaSourceParser("READCOMICSONLINE", "ReadComicsOnline.ru", "en", ContentType.COMICS)
internal class ReadComicsOnline(context: MangaLoaderContext) :
	MmrcmsParser(context, MangaParserSource.READCOMICSONLINE, "readcomicsonline.ru") {
	override val selectState = "dt:contains(Status)"
	override val selectTag = "dt:contains(Categories)"
}
