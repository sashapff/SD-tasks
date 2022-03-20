package search

import exceptions.RequestException
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock


class NewsManagerTest {
    private var manager: NewsManager? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val client = mock<NewsVkClient> {
            on { countNews(eq("creamsoda"), any(), any()) } doReturn 2
            on { countNews(eq("intelligency"), any(), any()) } doReturn 9
        }
        manager = NewsManager(client)
    }

    @Test
    fun testCountNews() {
        var newsCounts = manager!!.countNews("creamsoda", 1)
        Assert.assertEquals(arrayListOf(2), newsCounts)

        newsCounts = manager!!.countNews("intelligency", 3)
        Assert.assertEquals(arrayListOf(9, 9, 9), newsCounts)

        Assert.assertThrows(RequestException::class.java) {
            manager!!.countNews("intelligency", -1)
        }

        Assert.assertThrows(RequestException::class.java) {
            manager!!.countNews("creamsoda", 100)
        }
    }
}