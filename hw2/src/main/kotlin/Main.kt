import com.beust.klaxon.Klaxon
import config.VkConfig
import search.NewsManager
import search.NewsVkClient
import java.io.FileReader

fun main(args: Array<String>) {
    try {
        val hashtag = args[0]
        val nHours = args[1].toInt()

        if (nHours <= 0) {
            throw Exception("Number of hours should be positive");
        }

        val privateConfigPath = "../resources/privateConfig.json"
        val publicConfigPath = "../main/resources/publicConfig.json"

        val privateConfig = Klaxon().parse<VkConfig.PrivateConfig>(FileReader(privateConfigPath))
        val publicConfig = Klaxon().parse<VkConfig.PublicConfig>(FileReader(publicConfigPath))
        val token = privateConfig!!.token
        val host = publicConfig!!.host

        val newsVkClient = NewsVkClient(token, host)
        val newsManager = NewsManager(newsVkClient)

        println(newsManager.countNews(hashtag, nHours))

    } catch (e: Exception) {
        println("Invalid arguments. You should provide hashtag and number of hours. More: ${e.message}")
    }
}
