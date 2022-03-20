package geometry

class Line(private val from: Point, private val to: Point) {
    fun getFrom(): Point {
        return from
    }

    fun getTo(): Point {
        return to
    }
}