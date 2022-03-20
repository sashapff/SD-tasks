
import drawing_api.AwtDrawingApi
import drawing_api.FxDrawingApi
import graph.EdgesGraph
import graph.MatrixGraph

fun main(args: Array<String>) {
    if (args.size != 5) {
        error(
            "You should provide 4 options: canvas-height, canvas-width, circle-radius, <drawing_api> and <graph_type>, h " +
                    "and then you should enter you graph."
        )
    }

    val height = args[0].toLong()
    val width = args[1].toLong()
    val radius = args[2].toDouble()

    val drawingApi = if (args[3] == "awt") {
        AwtDrawingApi(height, width, radius)
    } else if (args[3] == "fx") {
        FxDrawingApi(height, width, radius)
    } else {
        error("<drawing_api> should be 'awt' or 'fx'")
    }

    val graph = if (args[4] == "matrix") {
        MatrixGraph(drawingApi)
    } else if (args[4] == "edges") {
        EdgesGraph(drawingApi)
    } else {
        error("<graph_type> should be 'matrix' or 'edges'")
    }

    graph.drawGraph()
}