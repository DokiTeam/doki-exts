package org.dokiteam.doki.parsers.site.gattsu.pt

import org.jsoup.nodes.Element
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.*
import org.dokiteam.doki.parsers.site.gattsu.GattsuParser
import org.dokiteam.doki.parsers.util.*

@MangaSourceParser("UNIVERSOHENTAI", "UniversoHentai", "pt", ContentType.HENTAI)
internal class UniversoHentai(context: MangaLoaderContext) :
	GattsuParser(context, MangaParserSource.UNIVERSOHENTAI, "universohentai.com") {

	override val tagPrefix = "category"

	override val filterCapabilities: MangaListFilterCapabilities
		get() = MangaListFilterCapabilities(
			isMultipleTagsSupported = true,
			isSearchSupported = true,
		)

	override suspend fun getFilterOptions() = MangaListFilterOptions(
		availableTags = fetchAvailableTags(),
	)

	private suspend fun fetchAvailableTags(): Set<MangaTag> {
		val doc = webClient.httpGet("https://$domain/tags/").parseHtml()
		return doc.requireElementById("menu-topo").parseTags()
	}

	override fun Element.parseTags() = select("a").mapNotNullToSet {
		if (!it.attr("href").contains("/category/")) return@mapNotNullToSet null
		val key = it.attr("href").removeSuffix("/").substringAfterLast("/")
		MangaTag(
			key = key,
			title = it.text(),
			source = source,
		)
	}

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val images = webClient.httpGet(chapter.url.toAbsoluteUrl(domain)).parseHtml().requireElementById("galeria")
			.select(".galeria-foto img")
		return images.map { img ->
			val urlImages = img.requireSrc()
			MangaPage(
				id = generateUid(urlImages),
				url = urlImages,
				preview = null,
				source = source,
			)
		}
	}

	override suspend fun getPageUrl(page: MangaPage): String = page.url.toAbsoluteUrl(domain)
}
