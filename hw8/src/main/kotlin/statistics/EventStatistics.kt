package statistics

import java.time.Instant

interface EventStatistics {
    fun incEvent(name: String)

    fun getEventStatisticByName(name: String): Map<Instant, Int>

    fun getAllEventStatistic() : Map<Instant, Int>

    fun printAllStatistic()

}