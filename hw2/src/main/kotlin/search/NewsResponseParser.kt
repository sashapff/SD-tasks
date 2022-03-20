package search

import com.google.gson.JsonParser
import exceptions.ResponseException

class NewsResponseParser {
    fun parseResponse(response: String): Int {
        try {
            val json = JsonParser.parseString(response).asJsonObject
            return json.asJsonObject["response"].asJsonObject["total_count"].asInt
        } catch (e: Exception) {
            throw ResponseException(
                "Invalid response format. " +
                        "Should be {\"response\": {\"items\": [],\"count\": <Int>,\"total_count\": <Int>>}}"
            )
        }
    }

}