package org.dokiteam.doki.parsers.site.fuzzydoodle.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.MangaState
import org.dokiteam.doki.parsers.site.fuzzydoodle.FuzzyDoodleParser
import java.util.*

@MangaSourceParser("LELSCANVF", "LelScanFr", "fr")
internal class LelScanVf(context: MangaLoaderContext) :
	FuzzyDoodleParser(context, MangaParserSource.LELSCANVF, "lelscanfr.com") {

	override val ongoingValue = "en-cours"
	override val finishedValue = "termin"

	override suspend fun getFilterOptions() = super.getFilterOptions().copy(
		availableStates = EnumSet.of(MangaState.ONGOING, MangaState.FINISHED),
	)
}
