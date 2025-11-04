package org.dokiteam.doki.parsers.site.madara.tr

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser
import org.dokiteam.doki.parsers.Broken

@Broken // The website is not functioning and displays the Plesk panel
@MangaSourceParser("STRAYFANSUB", "StrayFansub", "tr")
internal class StrayFansub(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.STRAYFANSUB, "strayfansub.com", 16) {
	override val tagPrefix = "seri-turu/"
}
