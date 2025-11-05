package org.dokiteam.doki.parsers.site.liliana.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.liliana.LilianaParser

@MangaSourceParser("MANHUAPLUSORG", "ManhuaPlus.org", "en")
internal class ManhuaPlusOrg(context: MangaLoaderContext) :
	LilianaParser(context, MangaParserSource.MANHUAPLUSORG, "manhuaplus.org")
