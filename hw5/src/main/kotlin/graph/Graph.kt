package graph

import drawing_api.DrawingApi
import geometry.Point
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

abstract class Graph(private val drawingApi: DrawingApi) {
    /**
     * Bridge to drawing api
     */
    private val width = drawingApi.getDrawingAreaWidth()
    private val height = drawingApi.getDrawingAreaHeight()
    private val center = Point((width).toDouble() / 2, (height).toDouble() / 2)
    private val radius: Double = (min(height, width)).toDouble() / 3
    var vertexNumber: Int = 1

    private fun vertexPosition(v: Int): Point {
        val turn = 2 * PI * v / vertexNumber
        return center + Point(cos(turn) * radius, sin(turn) * radius)
    }

    abstract fun readGraph()

    abstract fun printGraph()

    fun drawGraph() {
        readGraph()
        printGraph()
        drawingApi.showGraph()
    }

    fun drawVertex(v: Int) {
        drawingApi.drawCircle(vertexPosition(v), (min(height, width)).toDouble() / 30, v)
    }

    fun drawEdge(from: Int, to: Int) {
        drawingApi.drawLine(vertexPosition(from), vertexPosition(to))
    }
}