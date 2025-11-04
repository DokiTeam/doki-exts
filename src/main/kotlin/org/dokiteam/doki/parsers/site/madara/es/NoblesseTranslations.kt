package org.dokiteam.doki.parsers.site.madara.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("NOBLESSETRANSLATIONS", "NoblesseTranslations", "es")
internal class NoblesseTranslations(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.NOBLESSETRANSLATIONS, "nobledicion.yoveo.xyz")
