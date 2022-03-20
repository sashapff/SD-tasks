package graph

import drawing_api.DrawingApi
import geometry.Edge
import java.util.*
import kotlin.math.max

class EdgesGraph(private val drawingApi: DrawingApi) :
    Graph(drawingApi) {
    private val edges = ArrayList<Edge>()

    override fun readGraph() {
        val input = Scanner(System.`in`)
        val m = input.nextInt()
        for (i in (0 until m)) {
            val u = input.nextInt()
            val v = input.nextInt()
            edges.add(Edge(u - 1, v - 1));
            vertexNumber = max(vertexNumber, max(u, v))
        }
    }

    override fun printGraph() {
        for (i in (0 until edges.size)) {
            drawVertex(edges[i].from)
            drawVertex(edges[i].to)
            drawEdge(edges[i].from, edges[i].to)
        }
    }
}