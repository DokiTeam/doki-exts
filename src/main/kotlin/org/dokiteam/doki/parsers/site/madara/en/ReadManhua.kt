package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("READMANHUA", "ReadManhua (Broken)", "en", ContentType.HENTAI)
internal class ReadManhua(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.READMANHUA, "readmanhua.net", 20) {
	override val postReq = true
	override val datePattern = "d MMM yy"
}
