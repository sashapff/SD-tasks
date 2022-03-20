package geometry

class Point(private val x: Double, private val y: Double) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun times(mul: Double): Point {
        return Point(x * mul, y * mul)
    }

    fun getX(): Double {
        return x
    }

    fun getY(): Double {
        return x
    }
}