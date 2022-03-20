package http

import org.junit.Assert
import org.junit.ClassRule
import org.junit.Test
import rule.HostReachableRule


@HostReachableRule.HostReachable("api.vk.com")
class UrlReaderTest {
    @Test
    fun read() {
        val result = UrlReader()
            .readAsText("http://api.vk.com/method/newsfeed.search&q=creamsoda")
        Assert.assertTrue(result.isNotEmpty())
    }

    companion object {
        @get:ClassRule
        val rule = HostReachableRule()
    }
}