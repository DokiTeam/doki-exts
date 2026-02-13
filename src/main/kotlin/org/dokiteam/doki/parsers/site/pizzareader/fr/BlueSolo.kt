package org.dokiteam.doki.parsers.site.pizzareader.fr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.SortOrder
import org.dokiteam.doki.parsers.site.pizzareader.PizzaReaderParser
import java.util.EnumSet

@MangaSourceParser("BLUESOLO", "BlueSolo", "fr")
internal class BlueSolo(context: MangaLoaderContext) :
	PizzaReaderParser(context, MangaParserSource.BLUESOLO, "bluesolo.org") {

	override val availableSortOrders: Set<SortOrder> = EnumSet.of(
		SortOrder.ALPHABETICAL,
		SortOrder.UPDATED,
		SortOrder.UPDATED_ASC,
	)

	override val ongoingFilter = "en cours"
	override val completedFilter = "terminé"
	override val hiatusFilter = "hiatus"
	override val abandonedFilter = "cancel"
}
