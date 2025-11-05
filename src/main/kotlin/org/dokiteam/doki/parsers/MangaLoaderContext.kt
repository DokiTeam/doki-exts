package org.dokiteam.doki.parsers

import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Response
import org.dokiteam.doki.parsers.bitmap.Bitmap
import org.dokiteam.doki.parsers.config.MangaSourceConfig
import org.dokiteam.doki.parsers.model.MangaParserSource
import org.dokiteam.doki.parsers.model.MangaSource
import org.dokiteam.doki.parsers.util.LinkResolver
import org.dokiteam.doki.parsers.webview.InterceptedRequest
import java.util.*

public abstract class MangaLoaderContext {

	public abstract val httpClient: OkHttpClient

	public abstract val cookieJar: CookieJar

	public fun newParserInstance(source: MangaParserSource): MangaParser = source.newParser(this)

	public fun newLinkResolver(link: HttpUrl): LinkResolver = LinkResolver(this, link)

	public fun newLinkResolver(link: String): LinkResolver = newLinkResolver(link.toHttpUrl())

	public open fun encodeBase64(data: ByteArray): String = Base64.getEncoder().encodeToString(data)

	public open fun decodeBase64(data: String): ByteArray = Base64.getDecoder().decode(data)

	public open fun getPreferredLocales(): List<Locale> = listOf(Locale.getDefault())

	/**
	 * Execute JavaScript code and return result
	 * @param script JavaScript source code
	 * @return execution result as string, may be null
	 */
	@Deprecated("Provide a base url")
	public abstract suspend fun evaluateJs(script: String): String?

	/**
	 * Execute JavaScript code and return result
	 * @param script JavaScript source code
	 * @param baseUrl url of page script will be executed in context of
	 * @return execution result as string, may be null
	 */
	public abstract suspend fun evaluateJs(baseUrl: String, script: String): String?

	/**
	 * Open [url] in browser for some external action (e.g. captcha solving or non cookie-based authorization)
	 */
	public open fun requestBrowserAction(parser: MangaParser, url: String): Nothing {
		throw UnsupportedOperationException("Browser is not available")
	}

	public abstract fun getConfig(source: MangaSource): MangaSourceConfig

	public abstract fun getDefaultUserAgent(): String

	/**
	 * Helper function to be used in an interceptor
	 * to descramble images
	 * @param response Image response
	 * @param redraw lambda function to implement descrambling logic
	 */
	public abstract fun redrawImageResponse(
		response: Response,
		redraw: (image: Bitmap) -> Bitmap,
	): Response

	/**
	 * create a new empty Bitmap with given dimensions
	 */
	public abstract fun createBitmap(
		width: Int,
		height: Int,
	): Bitmap

	/**
	 * Intercept WebView requests with custom filtering logic
	 * Loads the specified URL in a WebView and captures HTTP requests that match the filter criteria.
	 *
	 * @param url The URL to load in the WebView
	 * @param interceptorScript JavaScript code that returns true/false for requests to capture
	 * @param timeout Maximum time to wait for requests (milliseconds)
	 * @return List of intercepted requests matching the filter criteria
	 */
	public open suspend fun interceptWebViewRequests(
		url: String,
		interceptorScript: String,
		timeout: Long = 30000L
	): List<InterceptedRequest> {
		throw UnsupportedOperationException("WebView request interception is not available")
	}

	/**
	 * Simplified API for capturing WebView URLs matching a pattern
	 *
	 * @param pageUrl The URL to load in the WebView
	 * @param urlPattern Regex pattern to match against request URLs
	 * @param timeout Maximum time to wait for requests (milliseconds)
	 * @return List of URLs that matched the pattern
	 */
	public open suspend fun captureWebViewUrls(
		pageUrl: String,
		urlPattern: Regex,
		timeout: Long = 30000L
	): List<String> {
		throw UnsupportedOperationException("WebView URL capture is not available")
	}

	/**
	 * Extract VRF (Verification Response Format) token from MangaFire-style AJAX requests
	 * Loads the page and captures AJAX requests containing VRF parameters.
	 *
	 * @param pageUrl The manga page URL to load
	 * @param timeout Maximum time to wait for VRF token (milliseconds)
	 * @return The extracted VRF token, or null if not found
	 */
	public open suspend fun extractVrfToken(
		pageUrl: String,
		timeout: Long = 15000L
	): String? {
		throw UnsupportedOperationException("VRF token extraction is not available")
	}
}
