package org.dokiteam.doki.parsers.site.mmrcms.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mmrcms.MmrcmsParser
import java.util.*

@Broken
@MangaSourceParser("MANGA_SCAN", "MangaScan", "fr")
internal class MangaScan(context: MangaLoaderContext) :
	MmrcmsParser(context, MangaParserSource.MANGA_SCAN, "mangascan-fr.net") {
	override val imgUpdated = ".jpg"
	override val sourceLocale: Locale = Locale.ENGLISH
}
