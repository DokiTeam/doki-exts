package org.dokiteam.doki.parsers.model

import org.dokiteam.doki.parsers.MangaParser

public data class MangaTag(
	/**
	 * User-readable tag title, should be in Title case
	 */
	@JvmField public val title: String,
	/**
	 * Identifier of a tag, must be unique among the source.
	 * @see MangaParser.getList
	 */
	@JvmField public val key: String,
	@JvmField public val source: MangaSource,
)
