package search

import com.xebialabs.restito.builder.stub.StubHttp
import com.xebialabs.restito.semantics.Action
import com.xebialabs.restito.semantics.Condition
import com.xebialabs.restito.semantics.Condition.method
import com.xebialabs.restito.server.StubServer
import exceptions.ResponseException
import org.glassfish.grizzly.http.Method
import org.junit.Assert
import org.junit.Test
import java.util.function.Consumer

class NewsVkClientStubTest {
    private var client: NewsVkClient = NewsVkClient("fake_token", "http://localhost:${PORT}")

    @Test
    fun testCorrectRequest() {
        withStubServer(PORT) { s: StubServer? ->
            StubHttp.whenHttp(s)
                .match(method(Method.GET), Condition.startsWithUri("/method/newsfeed.search"))
                .then(Action.stringContent("""{"response": {"items": [],"count": 639,"total_count": 639}}"""))
            val result = client.countNews("creamsoda", 0, 0)
            Assert.assertEquals(639, result)
        }
    }

    @Test
    fun testInvalidRequest() {
        withStubServer(PORT) { s: StubServer? ->
            StubHttp.whenHttp(s)
                .match(method(Method.GET), Condition.startsWithUri("/method/newsfeed.search"))
                .then(Action.stringContent("""{"items": [],"count": 639,"total_count": 639}"""))
            Assert.assertThrows(ResponseException::class.java) {
                client.countNews("creamsoda", 0, 0)
            }
        }
    }

    private fun withStubServer(port: Int, callback: Consumer<StubServer?>) {
        var stubServer: StubServer? = null
        try {
            stubServer = StubServer(port).run()
            callback.accept(stubServer)
        } finally {
            stubServer?.stop()
        }
    }

    companion object {
        private const val PORT = 32456
    }
}