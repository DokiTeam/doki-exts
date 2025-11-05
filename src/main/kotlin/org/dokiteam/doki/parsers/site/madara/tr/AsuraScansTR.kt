package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("ASURASCANSTR", "AsuraScansTR", "tr")
internal class AsuraScansTR(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.ASURASCANSTR, "asurascans.com.tr") {
	override val tagPrefix = "tur/"
}
