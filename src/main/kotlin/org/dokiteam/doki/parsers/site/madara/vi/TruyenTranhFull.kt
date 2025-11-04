package org.dokiteam.doki.parsers.site.madara.vi

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("TRUYENTRANHFULL", "Truyện Tranh Full", "vi")
internal class TruyenTranhFull(context: MangaLoaderContext) :
    MadaraParser(context, MangaParserSource.TRUYENTRANHFULL, "truyentranhfull.net", 20) {
    override val listUrl = "truyen-tranh/"
    override val tagPrefix = "the-loai/"
    override val datePattern = "dd/MM/yyyy"
}
