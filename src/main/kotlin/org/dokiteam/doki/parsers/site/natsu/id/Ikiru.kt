package org.dokiteam.doki.parsers.site.natsu.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.natsu.NatsuParser

@MangaSourceParser("IKIRU", "Ikiru", "id")
internal class Ikiru(context: MangaLoaderContext) :
	NatsuParser(context, MangaParserSource.IKIRU, pageSize = 24) {

	override val configKeyDomain = ConfigKey.Domain("02.ikiru.wtf")

	override fun onCreateConfig(keys: MutableCollection<ConfigKey<*>>) {
		super.onCreateConfig(keys)
		keys.add(userAgentKey)
	}
}
