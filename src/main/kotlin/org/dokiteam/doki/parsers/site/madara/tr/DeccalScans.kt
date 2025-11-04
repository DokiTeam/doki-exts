package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("DECCALSCANS", "DeccalScans", "tr", ContentType.HENTAI)
internal class DeccalScans(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.DECCALSCANS, "fuchscans.com") {
	override val tagPrefix = "turler/"
}