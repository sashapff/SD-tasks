package search

import exceptions.RequestException

class NewsManager(private val newsVkClient: NewsVkClient) {
    private fun fromHoursToSeconds(hours: Int): Long {
        return 1L * hours * 60 * 60
    }

    private fun fromMillisToSeconds(millis: Long): Long {
        return millis / 1000
    }

    fun countNews(hashtag: String, nHours: Int): List<Int> {
        if (nHours < 1 || nHours > 24) {
            throw RequestException("Number of hours should be between 1 and 24.")
        }

        var endTime: Long = fromMillisToSeconds(System.currentTimeMillis())
        var startTime: Long = endTime - fromHoursToSeconds(1)
        val statistics = ArrayList<Int>()
        for (i in 0 until nHours) {
            statistics.add(newsVkClient.countNews(hashtag, startTime, endTime))
            endTime = startTime
            startTime = endTime - fromHoursToSeconds(1)
        }
        return statistics
    }
}