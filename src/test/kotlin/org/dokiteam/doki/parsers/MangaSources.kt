package org.dokiteam.doki.parsers

import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE
import org.dokiteam.doki.parsers.model.MangaParserSource

// Change 'names' to test specified parsers
@EnumSource(MangaParserSource::class, names = [], mode = EnumSource.Mode.INCLUDE)
internal annotation class MangaSources
