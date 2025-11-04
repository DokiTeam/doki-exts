# doki-exts

This library provides a collection of manga parsers for convenient access manga available on the web. It can be used in
JVM and Android applications. This library is a fork / based on [kotatsu-parsers](https://github.com/KotatsuApp/kotatsu-parsers)

![Sources count](https://img.shields.io/badge/dynamic/yaml?url=https%3A%2F%2Fraw.githubusercontent.com%2FDokiTeam%2Fdoki-exts%2Frefs%2Fheads%2Fmain%2F.github%2Fsummary.yaml&query=total&label=manga%20sources&color=%23E9321C) [![](https://jitpack.io/v/DokiTeam/doki-exts.svg)](https://jitpack.io/#DokiTeam/doki-exts) ![License](https://img.shields.io/github/license/KotatsuApp/Kotatsu)

## Usage

1. Add it to your root build.gradle at the end of repositories:

   ```groovy
   allprojects {
	   repositories {
		   ...
		   maven { url 'https://jitpack.io' }
	   }
   }
   ```

2. Add the dependency

   For Java/Kotlin project:
    ```groovy
    dependencies {
        implementation("com.github.DokiTeam:doki-exts:$parsers_version")
    }
    ```

   For Android project:
    ```groovy
    dependencies {
        implementation("com.github.DokiTeam:doki-exts:$parsers_version") {
            exclude group: 'org.json', module: 'json'
        }
    }
    ```

   Versions are available on [JitPack](https://jitpack.io/#DokiTeam/doki-exts)

   When used in Android
   projects, [core library desugaring](https://developer.android.com/studio/write/java8-support#library-desugaring) with
   the [NIO specification](https://developer.android.com/studio/write/java11-nio-support-table) should be enabled to
   support Java 8+ features.


3. Usage in code

   ```kotlin
   val parser = mangaLoaderContext.newParserInstance(MangaParserSource.MANGADEX)
   ```

   `mangaLoaderContext` is an implementation of the `MangaLoaderContext` class.
   See examples
   of [Android](https://github.com/DokiTeam/Doki/blob/devel/app/src/main/kotlin/org/dokiteam/doki/core/parser/MangaLoaderContextImpl.kt)

   Note that the `MangaParserSource.DUMMY` parsers cannot be instantiated.

## How to add new source from Kotatsu to Doki ?

Just copy source (parser) from [kotatsu-parsers](https://github.com/KotatsuApp/kotatsu-parsers), change `org.koitharu.kotatsu.parsers` to `org.dokiteam.doki.parsers` and done. Create a new PR (Pull request) and i will approve it 👌

## Projects that use the library

- [Doki](https://github.com/DokiTeam/Doki)

## DMCA disclaimer

The developers of this application have no affiliation with the content available in the app. It is collected from
sources freely available through any web browser.
