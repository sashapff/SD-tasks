package search

import http.UrlReader
import java.net.URLEncoder

open class NewsVkClient(private val token: String, private val host: String) {
    private val reader = UrlReader()
    private val parser = NewsResponseParser()

    open fun countNews(hashtag: String, startTimeMillis: Long, endTimeMillis: Long): Int {
        val encodedHashtag = URLEncoder.encode("#${hashtag}", "utf-8")
        val url = "${host}/method/newsfeed.search?q=${encodedHashtag}" +
                "&start_time=${startTimeMillis}&end_time=${endTimeMillis}" +
                "&v=5.81&access_token=${token}"
        return parser.parseResponse(reader.readAsText(url))
    }

}