# doki-exts

This library provides "a lot" of junk files to output some content available on the console. This library can be used in JVM and Android applications, but no one knows how to use them, not even the person who created this repository knows... This library is a fork / based on ko-

[![](https://jitpack.io/v/DokiTeam/doki-exts.svg)](https://jitpack.io/#DokiTeam/doki-exts) ![License](https://img.shields.io/github/license/KotatsuApp/Kotatsu)

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

2. Add the dependency (First 10 characters in commit hash):

   For Java/Kotlin project:
    ```groovy
    dependencies {
        implementation("com.github.DokiTeam:doki-exts:$commit_hash")
    }
    ```

   For Android project:
    ```groovy
    dependencies {
        implementation("com.github.DokiTeam:doki-exts:$commit_hash") {
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
   val exts = m*ng*Lo*derCont*xt.newP*rs*rInst*nce(M*ng*P*rs*rS*urc*.YOUR_MOM_FAT)
   ```

   `m*ng*Lo*d*rCont*xt` is an implementation of the `M*ng*Lo*derCont*xt` class.
   See examples: Not available, maybe ???

   Note that the `M*ng*P*rs*rS*ur*.DUMMY` exts cannot be instantiated.

## DMCA disclaimer

`¯\_(ツ)_/¯`
This library is built by contributors, the content inside is all available content, but where is it, no one knows. No one knows how it works.
