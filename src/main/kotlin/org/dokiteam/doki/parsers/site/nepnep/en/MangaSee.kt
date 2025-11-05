package org.dokiteam.doki.parsers.site.nepnep.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.nepnep.NepnepParser

// site closed in favour of weeb central
@Broken
@MangaSourceParser("MANGASEE", "MangaSee", "en")
internal class MangaSee(context: MangaLoaderContext) :
	NepnepParser(context, MangaParserSource.MANGASEE, "mangasee123.com")
