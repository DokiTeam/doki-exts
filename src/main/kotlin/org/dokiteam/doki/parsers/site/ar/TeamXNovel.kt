package org.dokiteam.doki.parsers.site.ar

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.Headers
import org.jsoup.nodes.Element
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.core.PagedMangaParser
import org.dokiteam.doki.parsers.model.*
import org.dokiteam.doki.parsers.util.*
import java.util.*

@MangaSourceParser("TEAMXNOVEL", "TeamXNovel", "ar")
internal class TeamXNovel(context: MangaLoaderContext) :
	PagedMangaParser(context, MangaParserSource.TEAMXNOVEL, 10) {

	override val availableSortOrders: Set<SortOrder> = EnumSet.of(SortOrder.UPDATED, SortOrder.POPULARITY)

	override val configKeyDomain = ConfigKey.Domain("olympustaff.com")

	override fun onCreateConfig(keys: MutableCollection<ConfigKey<*>>) {
		super.onCreateConfig(keys)
		keys.add(userAgentKey)
	}

	override fun getRequestHeaders(): Headers = super.getRequestHeaders().newBuilder()
		.add("Referer", "https://$domain/")
		.build()

	override val filterCapabilities: MangaListFilterCapabilities
		get() = MangaListFilterCapabilities(
			isSearchSupported = true,
		)

	override suspend fun getFilterOptions() = MangaListFilterOptions(
		availableTags = fetchAvailableTags(),
		availableStates = EnumSet.of(MangaState.ONGOING, MangaState.FINISHED, MangaState.ABANDONED),
		availableContentTypes = EnumSet.of(
			ContentType.MANGA,
			ContentType.MANHWA,
			ContentType.MANHUA,
		),
	)

	override suspend fun getListPage(page: Int, order: SortOrder, filter: MangaListFilter): List<Manga> {
		val url = buildString {
			append("https://")
			append(domain)
			when {
				!filter.query.isNullOrEmpty() -> {
					append("/?search=")
					append(filter.query.urlEncoded())
					append("&page=")
					append(page)
				}

				else -> {
					if (order == SortOrder.UPDATED) {
						if (filter.tags.isNotEmpty() || filter.demographics.isNotEmpty()) {
							throw IllegalArgumentException("Updated sorting does not support other sorting filters")
						}
						append("/?page=")
						append(page.toString())
					} else {
						append("/series?page=")
						append(page.toString())

						filter.tags.oneOrThrowIfMany()?.let {
							append("&genre=")
							append(it.key)
						}

						filter.types.forEach {
							append("&type=")
							append(
								when (it) {
									ContentType.MANGA -> "مانجا ياباني"
									ContentType.MANHWA -> "مانهوا كورية"
									ContentType.MANHUA -> "مانها صيني"
									else -> ""
								},
							)
						}

						filter.states.oneOrThrowIfMany()?.let {
							append("status=")
							append(
								when (it) {
									MangaState.ONGOING -> "مستمرة"
									MangaState.FINISHED -> "مكتمل"
									MangaState.ABANDONED -> "متوقف"
									else -> "مستمرة"
								},
							)
						}
					}
				}
			}
		}
		val doc = webClient.httpGet(url).parseHtml()
		return doc.select("div.listupd .bs .bsx").ifEmpty {
			doc.select("div.post-body .box")
		}.map { div ->
			val href = div.selectFirstOrThrow("a").attrAsRelativeUrl("href")
			Manga(
				id = generateUid(href),
				title = div.select(".tt, h3").text(),
				altTitles = emptySet(),
				url = href,
				publicUrl = href.toAbsoluteUrl(domain),
				rating = RATING_UNKNOWN,
				contentRating = null,
				coverUrl = div.selectFirstOrThrow("img").src()?.replace("thumbnail_", ""),
				tags = emptySet(),
				state = when (div.selectFirst(".status")?.text()) {
					"مستمرة" -> MangaState.ONGOING
					"مكتمل" -> MangaState.FINISHED
					"متوقف" -> MangaState.ABANDONED
					else -> null
				},
				authors = emptySet(),
				source = source,
			)
		}
	}

	private suspend fun fetchAvailableTags(): Set<MangaTag> {
		val doc = webClient.httpGet("https://$domain/series").parseHtml()
		return doc.requireElementById("select_genre").select("option").mapToSet {
			MangaTag(
				key = it.attr("value"),
				title = it.text().toTitleCase(sourceLocale),
				source = source,
			)
		}
	}

	override suspend fun getDetails(manga: Manga): Manga {
		val doc = webClient.httpGet(manga.url.toAbsoluteUrl(domain)).parseHtml()
		val mangaUrl = manga.url.toAbsoluteUrl(domain)
		val maxPageChapterSelect = doc.select(".pagination .page-item a")
		var maxPageChapter = 1
		if (!maxPageChapterSelect.isNullOrEmpty()) {
			maxPageChapterSelect.map {
				val i = it.attr("href").substringAfterLast("=").toIntOrNull() ?: 1
				if (i > maxPageChapter) {
					maxPageChapter = i
				}
			}
		}
		return manga.copy(
			state = when (doc.selectFirstOrThrow(".full-list-info:contains(الحالة:) a").text()) {
				"مستمرة" -> MangaState.ONGOING
				"مكتمل" -> MangaState.FINISHED
				"متوقف" -> MangaState.ABANDONED
				else -> null
			},
			tags = doc.select(".review-author-info a").mapToSet { a ->
				MangaTag(
					key = a.attr("href").substringAfterLast("="),
					title = a.text(),
					source = source,
				)
			},
			description = doc.selectFirstOrThrow(".review-content").html(),
			chapters = run {
				if (maxPageChapter == 1) {
					parseChapters(doc)
				} else {
					coroutineScope {
						val result = ArrayList(parseChapters(doc))
						result.ensureCapacity(result.size * maxPageChapter)
						(2..maxPageChapter).map { i ->
							async {
								loadChapters(mangaUrl, i)
							}
						}.awaitAll()
							.flattenTo(result)
						result
					}
				}
			}.reversed(),
		)
	}

	private suspend fun loadChapters(baseUrl: String, page: Int): List<MangaChapter> {
		return parseChapters(webClient.httpGet("$baseUrl?page=$page").parseHtml().body())
	}

	// NEW: parseChapters rewritten to match the new TeamX HTML structure.
	// The site replaced the old ".eplister ul li" layout with a card-based grid:
	//   <div id="chaptersContainer">
	//     <div class="chapter-card" data-number="49" data-date="1774058776">
	//       <a class="chapter-link" href="/series/.../49">
	//         <div class="chapter-number">الفصل 49</div>
	//         <div class="chapter-title">متغير الموت</div>
	//       </a>
	//     </div>
	//   </div>
	// date is now a Unix timestamp (seconds) in the data-date attribute.
	private fun parseChapters(root: Element): List<MangaChapter> {
		return root.requireElementById("chaptersContainer").select("div.chapter-card")
			.map { card ->
				val url = card.selectFirstOrThrow("a.chapter-link").attrAsRelativeUrl("href")
				val chapterNumber = card.selectFirst(".chapter-number")?.text() ?: ""
				val chapterTitle = card.selectFirst(".chapter-title")?.text() ?: ""
				// Combine number + title, or fall back to just the number label
				val title = if (chapterTitle.isNotBlank() && chapterTitle != card.attr("data-number")) {
					"$chapterNumber - $chapterTitle"
				} else {
					chapterNumber
				}
				MangaChapter(
					id = generateUid(url),
					title = title,
					// data-number holds the chapter number directly (e.g. "49" or "37.5")
					number = card.attr("data-number").toFloatOrNull()
						?: url.substringAfterLast('/').toFloatOrNull() ?: 0f,
					volume = 0,
					url = url,
					scanlator = null,
					// data-date is a Unix timestamp in seconds → convert to milliseconds
					uploadDate = card.attr("data-date").toLongOrNull()?.times(1000L) ?: 0L,
					branch = null,
					source = source,
				)
			}
	}

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val fullUrl = chapter.url.toAbsoluteUrl(domain)
		val doc = webClient.httpGet(fullUrl).parseHtml()
		return doc.select(".image_list img, .image_list canvas").map { img ->
			val url = when {
				img.hasAttr("src") -> img.requireSrc().toRelativeUrl(domain)
				else -> img.attrAsRelativeUrl("data-src")
			}
			MangaPage(
				id = generateUid(url),
				url = url,
				preview = null,
				source = source,
			)
		}
	}
}