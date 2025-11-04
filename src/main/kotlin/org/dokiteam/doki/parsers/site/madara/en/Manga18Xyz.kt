package org.dokiteam.doki.parsers.site.madara.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@Broken
@MangaSourceParser("MANGA18XYZ", "Manga18.xyz", "en", ContentType.HENTAI)
internal class Manga18Xyz(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.MANGA18XYZ, "manga18.xyz", 36)
