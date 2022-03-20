import clock.DummyClockImpl
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import statistics.EventStatistics
import statistics.EventStatisticsImpl
import java.time.Instant
import java.time.temporal.ChronoUnit


class ClockTest {
    private var clock: DummyClockImpl? = null
    private var firstTime: Instant = Instant.EPOCH
    private var eventStatistic: EventStatistics? = null

    @Before
    fun setUp() {
        clock = DummyClockImpl(firstTime)
        eventStatistic = EventStatisticsImpl(clock!!)
    }

    @Test
    fun oneEvent() {
        val eventName = "test"
        eventStatistic!!.incEvent(eventName)
        var allStatistic = eventStatistic!!.getAllEventStatistic()
        var eventByNameStatistic = eventStatistic!!.getEventStatisticByName(eventName)
        Assertions.assertEquals(allStatistic, eventByNameStatistic)
        Assertions.assertEquals(allStatistic.size, 1)
        Assertions.assertEquals(allStatistic[firstTime.truncatedTo(ChronoUnit.MINUTES)], 1)

        eventStatistic!!.incEvent(eventName)

        allStatistic = eventStatistic!!.getAllEventStatistic()
        eventByNameStatistic = eventStatistic!!.getEventStatisticByName(eventName)
        Assertions.assertEquals(allStatistic, eventByNameStatistic)
        Assertions.assertEquals(allStatistic.size, 1)
        Assertions.assertEquals(allStatistic[firstTime.truncatedTo(ChronoUnit.MINUTES)], 2)

        clock!!.now = clock!!.now.plusSeconds(600)
        eventStatistic!!.incEvent(eventName)

        allStatistic = eventStatistic!!.getAllEventStatistic()
        eventByNameStatistic = eventStatistic!!.getEventStatisticByName(eventName)
        Assertions.assertEquals(allStatistic, eventByNameStatistic)
        Assertions.assertEquals(allStatistic.size, 2)
        Assertions.assertEquals(allStatistic[firstTime.truncatedTo(ChronoUnit.MINUTES)], 2)
        Assertions.assertEquals(allStatistic[firstTime.truncatedTo(ChronoUnit.MINUTES).plusSeconds(600)], 1)
    }

    @Test
    fun manyEvents() {
        val eventName = "test"
        val otherEventName = "other_test"
        eventStatistic!!.incEvent(eventName)
        eventStatistic!!.incEvent(otherEventName)
        val allStatistic = eventStatistic!!.getAllEventStatistic()
        val eventByNameStatistic = eventStatistic!!.getEventStatisticByName(eventName)
        val otherEventByNameStatistic = eventStatistic!!.getEventStatisticByName(otherEventName)

        Assertions.assertEquals(allStatistic.size, 1)
        Assertions.assertEquals(allStatistic[firstTime.truncatedTo(ChronoUnit.MINUTES)], 2)

        Assertions.assertEquals(eventByNameStatistic.size, 1)
        Assertions.assertEquals(eventByNameStatistic[firstTime.truncatedTo(ChronoUnit.MINUTES)], 1)

        Assertions.assertEquals(otherEventByNameStatistic.size, 1)
        Assertions.assertEquals(otherEventByNameStatistic[firstTime.truncatedTo(ChronoUnit.MINUTES)], 1)
    }

    @Test
    fun stressTest() {
        for (i in 1..100) {
            eventStatistic!!.incEvent("${i % 15}")
            clock!!.now = clock!!.now.plusSeconds(30)
        }

        for (i in 0..14) {
            eventStatistic!!.getEventStatisticByName("$i").values.forEach {
                Assertions.assertEquals(it, 1)
            }
        }
        eventStatistic!!.getAllEventStatistic().values.forEach {
            Assertions.assertEquals(it, 2)
        }
    }
}