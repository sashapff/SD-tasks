package drawing_api

import geometry.Line
import geometry.Point
import java.awt.Color
import java.awt.Frame
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import kotlin.system.exitProcess


class AwtDrawingApi(
    private val height: Long,
    private val width: Long,
    private val radius: Double,
) : DrawingApi,
    Frame() {
    private val circles: ArrayList<Pair<Point, Int>> = ArrayList()
    private val lines: ArrayList<Line> = ArrayList()

    override fun paint(g: Graphics) {
        val ga = g as Graphics2D
        ga.paint = Color.black
        for (circle in circles) {
            ga.fill(
                Ellipse2D.Double(
                    circle.first.getX() - radius / 2,
                    circle.first.getY() - radius / 2,
                    radius,
                    radius
                )
            )
            ga.drawString(
                circle.second.toString(),
                (circle.first.getX() - radius / 2).toFloat(),
                (circle.first.getY() - radius / 2).toFloat()
            )
        }
        for (line in lines) {
            ga.draw(
                Line2D.Double(
                    line.getFrom().getX(),
                    line.getFrom().getY(),
                    line.getTo().getX(),
                    line.getTo().getY()
                )
            )
        }
    }

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
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                exitProcess(0)
            }
        })
        setSize(width.toInt(), height.toInt())
        isVisible = true
    }
}
