package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("KIARA18", "Kiara18", "en", ContentType.HENTAI)
internal class Kiara18(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.KIARA18, "18.kiara.cool")
