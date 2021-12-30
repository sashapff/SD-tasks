package clock

import java.time.Instant

class ClockImpl : Clock {
    override fun now(): Instant {
        return Instant.now()
    }
}