package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("WICKEDWITCHSCAN", "WickedWitchScan", "pt")
internal class WickedWitchScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.WICKEDWITCHSCAN, "wicked-witch-scan.com", pageSize = 10) {
	override val postReq = true
}
