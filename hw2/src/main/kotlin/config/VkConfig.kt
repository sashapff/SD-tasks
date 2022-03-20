package config

import com.beust.klaxon.Klaxon
import java.io.InputStream

class VkConfig(publicConfig: InputStream, privateConfig: InputStream) {
    data class PrivateConfig(val token: String)

    data class PublicConfig(val host: String)

    val privateConfig: PrivateConfig = Klaxon().parse(
        privateConfig.bufferedReader().lines().toArray().joinToString()
    )!!

    val publicConfig: PublicConfig = Klaxon().parse(
        publicConfig.bufferedReader().lines().toArray().joinToString()
    )!!
}