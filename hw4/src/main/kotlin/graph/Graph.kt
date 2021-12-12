abstract class Graph {
    /**
     * Bridge to drawing api
     */
    private var drawingApi: DrawingApi? = null
    fun Graph(drawingApi: DrawingApi?) {
        this.drawingApi = drawingApi
    }

    abstract fun drawGraph()
}