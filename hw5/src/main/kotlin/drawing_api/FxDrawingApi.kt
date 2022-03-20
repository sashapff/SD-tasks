package drawing_api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import geometry.Line
import geometry.Point
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage


class FxDrawingApi(private val height: Long, private val width: Long, private val radius: Double) : DrawingApi {
    private val circles: ArrayList<Pair<Point, Int>> = ArrayList()
    private val lines: ArrayList<Line> = ArrayList()
    override fun getDrawingAreaWidth(): Long {
        return width
    }

    override fun getDrawingAreaHeight(): Long {
        return height
    }

    override fun drawCircle(v: Point, radius: Double, i: Int) {
        circles.add(Pair(v, i))
    }

    override fun drawLine(from: Point, to: Point) {
        lines.add(Line(from, to))
    }

    override fun showGraph() {
        launch(
            JavaFXApplication::class.java, width.toString(), height.toString(),
            gson.toJson(circles), gson.toJson(lines)
        )
    }

    class JavaFXApplication : Application() {
        override fun start(stage: Stage) {
            val parameters = parameters.raw
            if (parameters.size != 4) {
                throw RuntimeException()
            }

            val width = parameters[0].toInt()
            val height = parameters[1].toInt()
            val circles: ArrayList<Pair<Point, Int>> =
                gson.fromJson(parameters[2], object : TypeToken<ArrayList<Pair<Point, Int>>>() {}.type)
            val lines: List<Line> = gson.fromJson(parameters[3], object : TypeToken<List<Line?>?>() {}.type)

            val root = Group()
            val canvas = Canvas(width.toDouble(), height.toDouble())
            val graphicsContext = canvas.graphicsContext2D
            for (circle in circles) {
                graphicsContext.fillOval(
                    circle.first.getX() - staticRadius / 2,
                    circle.first.getY() - staticRadius / 2,
                    staticRadius,
                    staticRadius
                )
            }
            for (line in lines) {
                graphicsContext.strokeLine(
                    line.getFrom().getX(),
                    line.getFrom().getY(),
                    line.getTo().getX(),
                    line.getTo().getY()
                )
            }
            root.children.add(canvas)
            stage.scene = Scene(root)
            stage.show()
        }
    }

    companion object {
        private val gson = Gson()
        private const val staticRadius: Double = 20.0
    }
}
