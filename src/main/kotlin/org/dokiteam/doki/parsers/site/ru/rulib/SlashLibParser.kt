package org.dokiteam.doki.parsers.site.ru.rulib

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource

@MangaSourceParser("YAOILIB", "SlashLib", "ru")
internal class SlashLibParser(context: MangaLoaderContext) : LibSocialParser(
	context = context,
	source = MangaParserSource.YAOILIB,
	siteId = 2,
	siteDomains = arrayOf("v2.slashlib.me"),
)
