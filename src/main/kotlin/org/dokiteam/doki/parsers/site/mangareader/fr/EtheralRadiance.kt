package org.dokiteam.doki.parsers.site.mangareader.fr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.mangareader.MangaReaderParser
import org.dokiteam.doki.parsers.util.insertCookies

@Broken
@MangaSourceParser("ETHERALRADIANCE", "EtheralRadiance", "fr")
internal class EtheralRadiance(context: MangaLoaderContext) :
	MangaReaderParser(
		context,
		MangaParserSource.ETHERALRADIANCE,
		"www.etheralradiance.eu",
		pageSize = 20,
		searchPageSize = 10,
	) {

	override val filterCapabilities: MangaListFilterCapabilities
		get() = super.filterCapabilities.copy(
			isTagsExclusionSupported = false,
		)

	init {
		context.cookieJar.insertCookies(
			domain,
			"_lscache_vary=1;",
		)
	}
}
