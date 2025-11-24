package org.dokiteam.doki.parsers.site.natsu.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.natsu.NatsuParser

@MangaSourceParser("KIRYUU", "Kiryuu", "id")
internal class Kiryuu(context: MangaLoaderContext) :
	NatsuParser(context, MangaParserSource.KIRYUU, pageSize = 24) {

	override val configKeyDomain = ConfigKey.Domain("kiryuu03.com")

	override fun onCreateConfig(keys: MutableCollection<ConfigKey<*>>) {
		super.onCreateConfig(keys)
		keys.add(userAgentKey)
	}
}
