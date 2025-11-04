package org.dokiteam.doki.parsers.site.keyoapp.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.keyoapp.KeyoappParser

@Broken
@MangaSourceParser("LUASCANS", "luaComic.net", "en")
internal class LuaScans(context: MangaLoaderContext) :
	KeyoappParser(context, MangaParserSource.LUASCANS, "luacomic.org")
