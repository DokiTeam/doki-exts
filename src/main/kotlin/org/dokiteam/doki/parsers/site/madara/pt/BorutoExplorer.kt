package org.dokiteam.doki.parsers.site.madara.pt

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.madara.MadaraParser

@MangaSourceParser("BORUTOEXPLORER", "BorutoExplorer", "pt")
internal class BorutoExplorer(context: MangaLoaderContext) :
	MadaraParser(context, MangaParserSource.BORUTOEXPLORER, "leitor.borutoexplorer.com.br", 10) {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
