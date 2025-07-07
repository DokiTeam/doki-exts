package org.dokiteam.doki.parsers.site.madara.vi

import org.jsoup.nodes.Element
import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.exception.ParseException
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaChapter
import org.dokiteam.doki.parsers.model.MangaPage
import org.dokiteam.doki.parsers.model.MangaTag
import org.dokiteam.doki.parsers.model.MangaListFilter
import org.dokiteam.doki.parsers.model.MangaListFilterOptions
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.Manga
import org.dokiteam.doki.parsers.model.MangaState
import org.dokiteam.doki.parsers.model.ContentRating
import org.dokiteam.doki.parsers.model.SortOrder
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.config.ConfigKey
import org.dokiteam.doki.parsers.util.*
import org.dokiteam.doki.parsers.util.suspendlazy.getOrNull
import org.dokiteam.doki.parsers.util.suspendlazy.suspendLazy
import java.util.*

@MangaSourceParser("HENTAICUBE", "CBHentai", "vi", ContentType.HENTAI)
internal class HentaiCube(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.HENTAICUBE, "hentaicube.xyz") {

	override val datePattern = "dd/MM/yyyy"
	override val postReq = true
    override val authorSearchSupported = true
	override val postDataReq = "action=manga_views&manga="

    private val availableTags = suspendLazy(initializer = ::fetchTags)
	
	override suspend fun getFilterOptions() = MangaListFilterOptions(
		availableTags = availableTags.get(),
	)

    override suspend fun getListPage(page: Int, order: SortOrder, filter: MangaListFilter): List<Manga> {
		val pages = page + 1

        val url = buildString {
            if (!filter.author.isNullOrEmpty()) {
				clear()
                append("https://")
                append(domain)
                append("/tacgia/")
                append(filter.author.lowercase().replace(" ", "-"))
                
                if (pages > 1) {
                    append("/page/")
                    append(pages.toString())
                }
                
                append("/?m_orderby=")
                when (order) {
                    SortOrder.POPULARITY -> append("views")
                    SortOrder.UPDATED -> append("latest")
                    SortOrder.NEWEST -> append("new-manga")
                    SortOrder.ALPHABETICAL -> {}
                    SortOrder.RATING -> append("trending")
                    SortOrder.RELEVANCE -> {}
                    else -> append("latest") // default
                }
                return@buildString
            }

            append("https://")
            append(domain)

            if (pages > 1) {
                append("/page/")
                append(pages.toString())
            }
            
            append("/?s=")

            filter.query?.let {
                append(filter.query.urlEncoded())
            }

            append("&post_type=wp-manga")

            if (filter.tags.isNotEmpty()) {
                filter.tags.forEach {
                    append("&genre[]=")
                    append(it.key)
                }
            }

            filter.states.forEach {
                append("&status[]=")
                when (it) {
                    MangaState.ONGOING -> append("on-going")
                    MangaState.FINISHED -> append("end")
                    MangaState.ABANDONED -> append("canceled")
                    MangaState.PAUSED -> append("on-hold")
                    MangaState.UPCOMING -> append("upcoming")
                }
            }

            filter.contentRating.oneOrThrowIfMany()?.let {
                append("&adult=")
                append(
                    when (it) {
                        ContentRating.SAFE -> "0"
                        ContentRating.ADULT -> "1"
                        else -> ""
                    },
                )
            }

            if (filter.year != 0) {
                append("&release=")
                append(filter.year.toString())
            }

            append("&m_orderby=")
            when (order) {
                SortOrder.POPULARITY -> append("views")
                SortOrder.UPDATED -> append("latest")
                SortOrder.NEWEST -> append("new-manga")
                SortOrder.ALPHABETICAL -> append("alphabet")
                SortOrder.RATING -> append("rating")
                SortOrder.RELEVANCE -> {}
                else -> {}
            }
        }
        return parseMangaList(webClient.httpGet(url).parseHtml())
    }

	override suspend fun createMangaTag(a: Element): MangaTag? {
        val allTags = availableTags.getOrNull().orEmpty()
        val title = a.text().replace(Regex("\\(\\d+\\)"), "").trim() // force trim to remove space
        // compare to avoid duplicate tags with the same title
        return allTags.find { 
            it.title.trim().equals(title, ignoreCase = true) // try to search with trim
        }
    }

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val fullUrl = chapter.url.toAbsoluteUrl(domain)
		val doc = webClient.httpGet(fullUrl).parseHtml()
		val root = doc.body().selectFirst("div.main-col-inner")?.selectFirst("div.reading-content")
			?: throw ParseException("Root not found", fullUrl)
		return root.select("img").map { img ->
			val url = img.requireSrc().toRelativeUrl(domain)
			MangaPage(
				id = generateUid(url),
				url = url,
				preview = null,
				source = source,
			)
		}
	}

	private suspend fun fetchTags(): Set<MangaTag> {
        val doc = webClient.httpGet("https://$domain/the-loai-genres").parseHtml()
        val elements = doc.select("ul.list-unstyled li a")
        return elements.mapToSet { element ->
            val href = element.attr("href")
            val key = href.substringAfter("/theloai/").removeSuffix("/")
            val title = element.text().replace(Regex("\\(\\d+\\)"), "").trim() // force trim
            MangaTag(
                key = key,
                title = title,
                source = source,
            )
        }.toSet()
    }
}
