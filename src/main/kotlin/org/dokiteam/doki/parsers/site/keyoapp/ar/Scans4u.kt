package org.dokiteam.doki.parsers.site.keyoapp.ar

import org.dokiteam.doki.parsers.MangaLoaderContext
import org.dokiteam.doki.parsers.MangaSourceParser
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.site.keyoapp.KeyoappParser

@MangaSourceParser("SCANS4U", "4uScans", "ar")
internal class Scans4u(context: MangaLoaderContext) :
	KeyoappParser(context, MangaParserSource.SCANS4U, "4uscans.com")
