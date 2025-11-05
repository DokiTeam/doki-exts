package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("ALLIED_FANSUB", "AlliedFansub", "tr", ContentType.HENTAI)
internal class AlliedFansub(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ALLIED_FANSUB, "alliedfansub.net", 20) {
	override val datePattern = "dd/MM/yyyy"
}