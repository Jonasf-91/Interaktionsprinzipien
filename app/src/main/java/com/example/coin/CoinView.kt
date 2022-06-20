package com.example.coin.com.example.coin

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*


class CoinView(context:Context, attributeSet: AttributeSet)
    : View(context, attributeSet){

    private var x = 540
    private var y = 400
    var coin = Coin()
    lateinit var printBitmap: Bitmap
    lateinit var printCanvas: Canvas

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        printBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        printCanvas = Canvas(printBitmap)

    }

    override fun onDraw(canvas: Canvas) {

        val path = createPolygon(coin.corners, 250f)
        val paint = Paint()

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

        // draw finger path
        paint.setStrokeWidth(5f)
        canvas.drawBitmap(printBitmap, 0f, 0f, paint)
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

    fun update(newCoin: Coin){
        coin = newCoin
        invalidate()
        requestLayout()
    }

    fun update(){
        invalidate()
        requestLayout()
    }

    fun updatePrint(bitmap : Bitmap){
        printBitmap = bitmap
        invalidate()
        requestLayout()
    }



}