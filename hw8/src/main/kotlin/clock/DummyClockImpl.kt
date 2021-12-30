package clock

import java.time.Instant

class DummyClockImpl(var now: Instant) : Clock {
    override fun now(): Instant {
        return now
    }
}