package drawing_api

import geometry.Point


interface DrawingApi {
    fun getDrawingAreaWidth(): Long
    fun getDrawingAreaHeight(): Long
    fun drawCircle(v: Point, radius: Double, i: Int)
    fun drawLine(from: Point, to: Point)
    fun showGraph()
}