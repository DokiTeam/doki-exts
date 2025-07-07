package org.dokiteam.doki.parsers.site.vi.yurigarden

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.vi.yurigarden.YuriGardenParser

@MangaSourceParser("YURIGARDEN_R18", "Yuri Garden (18+)", "vi", type = ContentType.HENTAI)
internal class YuriGardenR18(context: MangaLoaderContext) :
	YuriGardenParser(
		context = context,
		source = MangaParserSource.YURIGARDEN_R18,
		domain = "yurigarden.com",
		isR18Enable = true
	)
