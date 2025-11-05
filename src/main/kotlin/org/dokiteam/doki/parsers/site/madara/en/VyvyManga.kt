package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.Broken

@Broken // It has become obsolete and has been replaced by the new VyManga parser.
@MangaSourceParser("VYVYMANGA", "VyvyManga", "en")
internal class VyvyManga(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.VYVYMANGA, "vyvymanga.org")
