package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("SLEEPYTRANSLATIONS", "Sleepy Translations", "en")
internal class SleepyTranslations(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.SLEEPYTRANSLATIONS, "sleepytranslations.com", 16) {
	override val listUrl = "series/"
	override val tagPrefix = "genre/"
}
