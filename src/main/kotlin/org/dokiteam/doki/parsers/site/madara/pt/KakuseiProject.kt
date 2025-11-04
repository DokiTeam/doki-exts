package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("KAKUSEIPROJECT", "KakuseiProject", "pt")
internal class KakuseiProject(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.KAKUSEIPROJECT, "kakuseiproject.com", 10)
