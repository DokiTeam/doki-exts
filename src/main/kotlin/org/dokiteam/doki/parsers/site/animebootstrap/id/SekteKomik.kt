package org.dokiteam.doki.parsers.site.animebootstrap.id

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.animebootstrap.AnimeBootstrapParser

@MangaSourceParser("SEKTEKOMIK", "SekteKomik", "id")
internal class SekteKomik(context: MangaLoaderContext) :
	AnimeBootstrapParser(context, MangaParserSource.SEKTEKOMIK, "sektekomik.xyz")
