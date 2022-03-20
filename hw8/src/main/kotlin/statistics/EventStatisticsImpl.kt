package statistics

import clock.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class EventStatisticsImpl(private var clock: Clock) : EventStatistics {
    private val events: MutableMap<String, Deque<Instant>> = mutableMapOf()

    private fun removeOldEvents(now: Instant) {
        val threshold = now.minusSeconds(3600)
        for (event in events) {
            while (!event.value.isEmpty() && event.value.peekFirst().isBefore(threshold))
                event.value.removeFirst()
            if (event.value.isEmpty())
                events.remove(event.key)
        }
    }

    override fun incEvent(name: String) {
        val now = clock.now()
        if (name in events)
            events[name]!!.addLast(now)
        else
            events[name] = ArrayDeque(listOf(now))
    }

    override fun getEventStatisticByName(name: String): Map<Instant, Int> {
        removeOldEvents(clock.now())
        val statistic = mutableMapOf<Instant, Int>()
        if (name in events)
            for (time in events[name]!!) {
                val minute = time.truncatedTo(ChronoUnit.MINUTES)
                if (minute in statistic)
                    statistic[minute] = statistic[minute]!! + 1
                else
                    statistic[minute] = 1;
            }
        return statistic
    }

    override fun getAllEventStatistic(): Map<Instant, Int> {
        removeOldEvents(clock.now())
        val statistic = mutableMapOf<Instant, Int>()
        for (event in events) {
            for (time in event.value) {
                val minute = time.truncatedTo(ChronoUnit.MINUTES)
                if (minute in statistic)
                    statistic[minute] = statistic[minute]!! + 1
                else
                    statistic[minute] = 1;
            }
        }
        return statistic
    }

    override fun printAllStatistic() {
        val statistic = getAllEventStatistic()
        for (minute in statistic.keys) {
            println("Minute=${minute}: rpm=${statistic[minute]}")
        }
    }
}