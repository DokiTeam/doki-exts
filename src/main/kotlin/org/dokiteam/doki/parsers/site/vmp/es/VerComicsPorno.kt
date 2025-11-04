package org.dokiteam.doki.parsers.site.vmp.es

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.ContentType
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.vmp.VmpParser

// Other domain name : toonx.net
@MangaSourceParser("VERCOMICSPORNO", "VerComicsPorno", "es", ContentType.HENTAI)
internal class VerComicsPorno(context: MangaLoaderContext) :
	VmpParser(context, MangaParserSource.VERCOMICSPORNO, "vercomicsporno.com") {
	override val listUrl = "comics-porno/"
	override val geneUrl = "etiquetas/"
}
