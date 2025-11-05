package org.dokiteam.doki.parsers.site.mmrcms.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mmrcms.MmrcmsParser
import java.util.*

@Broken
@MangaSourceParser("JPSCANVF", "LireScanVf.com", "fr")
internal class JpScanVf(context: MangaLoaderContext) :
	MmrcmsParser(context, MangaParserSource.JPSCANVF, "lirescanvf.com") {
	override val sourceLocale: Locale = Locale.ENGLISH
}
