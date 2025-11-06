package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.Broken

@Broken
@MangaSourceParser("TECNOPROJECTS", "TecnoProjects", "es")
internal class TecnoProjects(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.TECNOPROJECTS, "tecnoprojects.xyz") {
	override val datePattern = "dd 'de' MMMM 'de' yyyy"
}
