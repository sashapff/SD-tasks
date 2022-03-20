package graph

import drawing_api.DrawingApi
import java.util.*

class MatrixGraph(private val drawingApi: DrawingApi) :
    Graph(drawingApi) {
    private val matrix = arrayListOf<ArrayList<Int>>()

    override fun readGraph() {
        val input = Scanner(System.`in`)
        vertexNumber = input.nextInt()
        for (i in (0 until vertexNumber)) {
            matrix.add(arrayListOf());
        }
        for (u in (0 until vertexNumber)) {
            for (v in (0 until vertexNumber)) {
                val e = input.nextInt()
                matrix[u].add(e);
            }
        }
    }

    override fun printGraph() {
        for (u in (0 until matrix.size)) {
            drawVertex(u)
            for (v in (0 until matrix[u].size)) {
                if (matrix[u][v] == 1) {
                    drawEdge(u, v)
                }
            }
        }
    }
}