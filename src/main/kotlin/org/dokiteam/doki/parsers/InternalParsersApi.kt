package org.dokiteam.doki.parsers

/**
 * This marker distinguishes the internal API and is used to opt-in for that feature when parsers developing.
 *
 * Any usage of a declaration annotated with `@InternalParsersApi` must be accepted either by
 * annotating that usage with the [OptIn] annotation, e.g. `@OptIn(InternalParsersApi::class)`,
 * or by using the compiler argument `-opt-in=org.dokiteam.doki.parsers.InternalParsersApi`.
 */
@Retention(AnnotationRetention.BINARY)
@SinceKotlin("1.3")
@RequiresOptIn
@MustBeDocumented
public annotation class InternalParsersApi
