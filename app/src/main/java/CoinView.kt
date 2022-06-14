package com.example.shapedrawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import java.util.*


class CoinView(context:Context, attributeSet: AttributeSet)
    : View(context, attributeSet){

    private var x = 540
    private var y = 350

    override fun onDraw(canvas: Canvas) {

        val path = createPath(8, 250f)
        val paint = Paint()

        val color = randomColor()

        paint.setStyle(Paint.Style.FILL)
        paint.setColor(color)
        canvas.drawPath(path, paint)

        paint.setStyle(Paint.Style.STROKE)
        paint.setColor(manipulateColor(color,0.8f))
        paint.setStrokeWidth(20f)
        paint.setStrokeCap(Paint.Cap.ROUND)

        canvas.drawPath(path, paint)

    }

    fun createPath(sides: Int, radius: Float): Path {
        val path = Path()
        val angle = 2.0 * Math.PI / sides
        path.moveTo(
            x + (radius * Math.cos(0.0)).toFloat(),
            y + (radius * Math.sin(0.0)).toFloat())
        for (i in 1 until sides) {
            path.lineTo(
                x + (radius * Math.cos(angle * i)).toFloat(),
                y + (radius * Math.sin(angle * i)).toFloat())
        }
        path.close()
        return path
    }

    fun randomColor(): Int {
       val rnd = Random()
       return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(
            a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255)
        )
    }


}