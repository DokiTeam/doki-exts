package org.dokiteam.doki.parsers.site.pizzareader.it

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.pizzareader.PizzaReaderParser

@MangaSourceParser("TUTTOANIMEMANGA", "TuttoAnimeManga", "it")
internal class TuttoAnimeManga(context: MangaLoaderContext) :
	PizzaReaderParser(context, MangaParserSource.TUTTOANIMEMANGA, "tuttoanimemanga.net") {
	override val completedFilter = "completato"
}
