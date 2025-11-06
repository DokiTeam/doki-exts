package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("FIRSTKISSMANHUA", "FirstKissManhua", "en")
internal class FirstKissManhua(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.FIRSTKISSMANHUA, "1stkissmanhua.net", 20) {
	override val listUrl = "manhua/"
}
