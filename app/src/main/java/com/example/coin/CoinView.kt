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
    var printBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    lateinit var coinBitmap: Bitmap
    lateinit var coinCanvas: Canvas
    val paint = Paint()

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        x = width/2
        y = height/2
        coinBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        coinCanvas = Canvas(coinBitmap)




    }

    override fun onDraw(canvas: Canvas) {

        coinBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        coinCanvas = Canvas(coinBitmap)
        val path = createPolygon(coin.corners, 250f)


        // draw Fill
        paint.setStyle(Paint.Style.FILL)
        paint.setColor(coin.fillColor)
        coinCanvas.drawPath(path, paint)

        // draw Stroke
        paint.setStyle(Paint.Style.STROKE)
        paint.setColor(coin.strokeColor)
        paint.setStrokeWidth(20f)
        paint.setStrokeCap(Paint.Cap.ROUND)
        coinCanvas.drawPath(path, paint)

        // draw finger path
        val positionX = (width/2)-printBitmap.width/2
        val positionY = (height/2)-printBitmap.height/2
        coinCanvas.drawBitmap(printBitmap, positionX.toFloat(), positionY.toFloat(), null)

        canvas.drawBitmap(coinBitmap,0f, 0f, null)
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

    }

    fun update(){
        invalidate()
        requestLayout()
    }

    fun updatePrint(bitmap : Bitmap){
        printBitmap = bitmap
        invalidate()

    }

    fun update(newCoin:Coin, bitmap : Bitmap){
        printBitmap = bitmap
        coin = newCoin
        invalidate()

    }



}