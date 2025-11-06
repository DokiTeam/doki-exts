package org.dokiteam.doki.parsers.site.madtheme.en

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaPage
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madtheme.MadthemeParser
import org.dokiteam.doki.parsers.util.toAbsoluteUrl
import org.dokiteam.doki.parsers.util.generateUid
import org.dokiteam.doki.parsers.util.parseHtml

@MangaSourceParser("MANGAPUMA", "MangaPuma", "en")
internal class MangaPuma(context: MangaLoaderContext) :
	MadthemeParser(context, MangaParserSource.MANGAPUMA, "mangapuma.com") {

	private val subDomain = "sb.mbcdn.xyz"

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val fullUrl = chapter.url.toAbsoluteUrl(domain)
		val doc = webClient.httpGet(fullUrl).parseHtml()
		val regexPages = Regex("chapImages\\s*=\\s*['\"](.*?)['\"]")
		val pages = doc.select("script").firstNotNullOfOrNull { script ->
			regexPages.find(script.html())?.groupValues?.getOrNull(1)
		}?.split(',')

		return pages?.map { url ->
			val cleanUrl = url.substringAfter("/manga")
			MangaPage(
				id = generateUid(url),
				url = "https://$subDomain/manga$cleanUrl",
				preview = null,
				source = source,
			)
		} ?: emptyList()
	}

}
