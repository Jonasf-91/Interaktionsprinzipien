package com.example.coin

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.example.coin.com.example.coin.CoinView
import com.example.interaktionsprinzipien.R
import kotlinx.android.synthetic.main.activity_editor_draw.view.*
import java.lang.reflect.Constructor


class DrawingView(context:Context, attributeSet: AttributeSet)
: View(context, attributeSet)  {

    private lateinit var extraCanvas: Canvas
    lateinit var extraBitmap: Bitmap

    private val backgroundColor = ResourcesCompat.getColor(resources,R.color.colorBackground, null)
    private val STROKE_WIDTH = 20f
            var drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
            var paint = Paint()
    var path = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop



    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint = Paint().apply {
            color = drawColor
            // Smooths out edges of what is drawn without affecting shape.
            isAntiAlias = true
            // Dithering affects how colors with higher-precision than the device are down-sampled.
            isDither = true
            style = Paint.Style.STROKE // default: FILL
            strokeJoin = Paint.Join.ROUND // default: MITER
            strokeCap = Paint.Cap.ROUND // default: BUTT
            strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
        }
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to cache it.
            extraCanvas.drawPath(path, paint)


        }
        invalidate()

    }

    private fun touchUp() {
        path.reset()
    }

    fun cleanBitmap(){
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        invalidate()
    }

}