package http

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UncheckedIOException
import java.net.MalformedURLException
import java.net.URL

class UrlReader {
    fun readAsText(sourceUrl: String): String {
        val url = toUrl(sourceUrl)
        try {
            BufferedReader(InputStreamReader(url.openStream())).use { `in` ->
                val buffer = StringBuilder()
                var inputLine: String?
                while (`in`.readLine().also { inputLine = it } != null) {
                    buffer.append(inputLine)
                    buffer.append("\n")
                }
                return buffer.toString()
            }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    private fun toUrl(url: String): URL {
        return try {
            URL(url)
        } catch (e: MalformedURLException) {
            throw RuntimeException("Malformed url: $url")
        }
    }
}