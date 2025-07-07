package org.dokiteam.doki.parsers.site.vi.yurigarden

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.vi.yurigarden.YuriGardenParser

@MangaSourceParser("YURIGARDEN", "Yuri Garden", "vi")
internal class YuriGarden(context: MangaLoaderContext) :
	YuriGardenParser(
		context = context,
		source = MangaParserSource.YURIGARDEN,
		domain = "yurigarden.com",
		isR18Enable = false
	)
