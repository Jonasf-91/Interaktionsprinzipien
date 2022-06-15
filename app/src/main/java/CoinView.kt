package com.example.coin

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
    var coin = Coin()

    override fun onDraw(canvas: Canvas) {

        val path = createPolygon(coin.corners, 250f)
        val paint = Paint()

        //val color = randomColor()

        // draw Fill
        paint.setStyle(Paint.Style.FILL)
        paint.setColor(coin.fillColor)
        canvas.drawPath(path, paint)

        // draw Stroke
        paint.setStyle(Paint.Style.STROKE)
        paint.setColor(coin.strokeColor)
        paint.setStrokeWidth(20f)
        paint.setStrokeCap(Paint.Cap.ROUND)
        canvas.drawPath(path, paint)

    }

    fun createPolygon(sides: Int, radius: Float): Path {
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

    fun setRandomColor(){
        coin.setColor(randomColor())
        invalidate()
        requestLayout()
    }

    fun randomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    fun update(newCoin:Coin){
        coin = newCoin
        invalidate()
        requestLayout()
    }



}