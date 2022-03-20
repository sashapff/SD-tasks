package http

import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.status
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.method
import com.xebialabs.restito.semantics.Condition.startsWithUri
import com.xebialabs.restito.server.StubServer
import org.glassfish.grizzly.http.Method
import org.glassfish.grizzly.http.util.HttpStatus
import org.junit.Assert
import org.junit.Test
import java.io.UncheckedIOException
import java.util.function.Consumer


class UrlReaderWithStubServerTest {
    private val urlReader = UrlReader()

    @Test
    fun readAsText() {
        withStubServer(PORT, Consumer<StubServer?> { s: StubServer? ->
            whenHttp(s)
                .match(method(Method.GET), startsWithUri("/ping"))
                .then(stringContent("pong"))
            val result =
                urlReader.readAsText("http://localhost:$PORT/ping")
            Assert.assertEquals("pong\n", result)
        })
    }

    @Test(expected = UncheckedIOException::class)
    fun readAsTextWithNotFoundError() {
        withStubServer(PORT, Consumer<StubServer?> { s: StubServer? ->
            whenHttp(s)
                .match(method(Method.GET), startsWithUri("/ping"))
                .then(status(HttpStatus.NOT_FOUND_404))
            urlReader.readAsText("http://localhost:$PORT/ping")
        })
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
        private const val PORT = 32453
    }
}