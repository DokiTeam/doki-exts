package org.dokiteam.doki.parsers.site.heancms.en

import org.dokiteam.doki.parsers.Broken
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.heancms.HeanCms

@Broken("Not dead, changed template")
@MangaSourceParser("TEMPLESCAN", "TempleScan", "en")
internal class TempleScan(context: MangaLoaderContext) :
	HeanCms(context, MangaParserSource.TEMPLESCAN, "templetoons.com") {
	override val pathManga = "comic"

	override val apiPath: String
		get() = "$domain/api"
}
