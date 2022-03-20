package search

import exceptions.ResponseException
import org.junit.Assert
import org.junit.Test

class NewsResponseParserTest {
    private val parser = NewsResponseParser()

    @Test
    fun testParseResponse() {
        val count: Int = parser.parseResponse(
            """{"response": {"items": [],"count": 639,"total_count": 639}}"""
        )
        Assert.assertTrue(count == 639)
    }

    @Test
    fun testParseInvalidResponse() {
        Assert.assertThrows(ResponseException::class.java) {
            parser.parseResponse(
                """{"items": [],"count": 639,"total_count": 639}"""
            )
        }

        Assert.assertThrows(ResponseException::class.java) {
            parser.parseResponse(
                """{"response": {"items": [],"count": 639,"total_counts": 639}}"""
            )
        }

        Assert.assertThrows(ResponseException::class.java) {
            parser.parseResponse(
                """{"response": {"items": [],"count": 639,"total_count": "zero"}}"""
            )
        }
    }

}