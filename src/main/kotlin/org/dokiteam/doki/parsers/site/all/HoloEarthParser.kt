package org.dokiteam.doki.parsers.site.all

import org.dokiteam.doki.parsers.model.RATING_UNKNOWN
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.core.LegacyPagedMangaParser
import org.dokiteam.doki.parsers.model.Manga
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaListFilterCapabilities
import org.dokiteam.doki.parsers.model.MangaListFilterOptions
import org.dokiteam.doki.parsers.model.MangaPage
import org.dokiteam.doki.parsers.model.SortOrder
import org.dokiteam.doki.parsers.model.*
import org.dokiteam.doki.parsers.model.search.*
import org.dokiteam.doki.parsers.util.generateUid
import org.dokiteam.doki.parsers.util.parseHtml
import org.dokiteam.doki.parsers.util.selectFirstOrThrow
import org.dokiteam.doki.parsers.util.tryParse
import java.text.SimpleDateFormat
import java.util.Locale

@MangaSourceParser("HOLOEARTH", "HoloEarth")
internal class HoloEarthParser(context: MangaLoaderContext) :
    LegacyPagedMangaParser(context, MangaParserSource.HOLOEARTH, 3) {

	override val configKeyDomain: ConfigKey.Domain
		get() = ConfigKey.Domain("holoearth.com")

	override fun onCreateConfig(keys: MutableCollection<ConfigKey<*>>) {
		super.onCreateConfig(keys)
		keys.add(userAgentKey)
	}

	override val availableSortOrders: Set<SortOrder> = setOf(SortOrder.NEWEST)

	override val filterCapabilities: MangaListFilterCapabilities
        get() = MangaListFilterCapabilities(
            isSearchSupported = false,
        )

	override suspend fun getFilterOptions() = MangaListFilterOptions(
		availableLocales = setOf(
            Locale("en"),
            Locale.JAPANESE,
            Locale("id"),
        ),
	)

	override suspend fun getListPage(page: Int, order: SortOrder, filter: MangaListFilter): List<Manga> {
		val url = buildString {
			append("https://$domain")

			filter.locale?.let {
                append(
                    when (it) {
                        Locale("en") -> "/en"
						Locale.JAPANESE -> ""
                        Locale("id") -> "/id"
                        else -> "" // default
                    }
                )
            }

			append("/alt/holonometria/manga")
		}

		val doc = webClient.httpGet(url).parseHtml()
		val root = doc.body().selectFirstOrThrow(".manga__list")
		val mangaList = root.select("li .manga__item-inner")

		if (mangaList.isEmpty()) return emptyList()

		return mangaList.mapNotNull { li ->
			val coverUrl = li.getElementsByTag("img").attr("src")
			val title = li.getElementsByClass("manga__title").text()
			val altTitle = li.getElementsByClass("manga__copy").text()
			val description = li.getElementsByClass("manga__caption").text()
			val url = li.getElementsByTag("a").attr("href")

			Manga(
				id = generateUid(url),
				title = title,
				altTitles = setOf(altTitle),
				url = url,
				publicUrl = url,
				rating = RATING_UNKNOWN,
				contentRating = null,
				coverUrl = coverUrl,
				tags = emptySet(),
				state = null,
				authors = emptySet(),
				source = source,
				description = description,
			)
		}
	}

	override suspend fun getDetails(manga: Manga): Manga {
		val doc = webClient.httpGet(manga.url).parseHtml()
		val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.US)
		val root = doc.body().selectFirstOrThrow(".manga-detail__wrapper")
		val coverUrl = root.selectFirstOrThrow(".manga-detail__thumb img").attr("src")
		val chapters = root.select(".manga-detail__list-item")
		val mangaChapters = chapters.mapIndexed { index, li ->
			val url = li.selectFirstOrThrow(".manga-detail__list-link").attr("href")
			val title = li.selectFirstOrThrow(".manga-detail__list-title").text()
			val dateStr = li.selectFirstOrThrow(".manga-detail__list-date").text()
			val uploadDate = dateFormat.tryParse(dateStr) ?: 0L
			val scanlator = root.selectFirst(".manga-detail__person")?.text()

			MangaChapter(
				id = generateUid(url),
				title = title,
				number = index + 1f,
				volume = 0,
				url = url,
				scanlator = scanlator,
				uploadDate = uploadDate,
				branch = null,
				source = source,
			)
		}

		return manga.copy(
			coverUrl = coverUrl,
			chapters = mangaChapters,
		)
	}


	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val doc = webClient.httpGet(chapter.url).parseHtml()
		val imageList = doc.body().selectFirstOrThrow(".manga-detail__swiper-wrapper")
		val images = imageList.select(".manga-detail__swiper-slide").reversed()

		return images.mapNotNull { page ->
			val img = page.selectFirst("img") ?: return@mapNotNull null
			val src = img.attr("src")
			MangaPage(
				id = generateUid(src),
				url = src,
				preview = src,
				source = source,
			)
		}
	}
}
