package search

import com.beust.klaxon.Klaxon
import config.VkConfig
import org.junit.Assert
import org.junit.ClassRule
import org.junit.Test
import rule.HostReachableRule
import java.io.FileReader

@HostReachableRule.HostReachable("api.vk.com")
class NewsVkClientIntegrationTest {
    @Test
    fun testCountNews() {
        val privateConfigPath = "src/main/resources/privateConfig.json"
        val publicConfigPath = "src/main/resources/publicConfig.json"

        val privateConfig = Klaxon().parse<VkConfig.PrivateConfig>(FileReader(privateConfigPath))
        val publicConfig = Klaxon().parse<VkConfig.PublicConfig>(FileReader(publicConfigPath))
        val token = privateConfig!!.token
        val host = publicConfig!!.host

        val client = NewsVkClient(token, host)

        val curUnixTime = System.currentTimeMillis() / 1000
        val count = client.countNews("creamsoda", curUnixTime - 1000, curUnixTime)
        Assert.assertTrue(count >= 0)
    }


    companion object {
        @get:ClassRule
        val rule = HostReachableRule()
    }
}