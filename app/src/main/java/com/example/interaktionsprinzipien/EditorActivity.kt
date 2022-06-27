package com.example.interaktionsprinzipien

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.coin.DrawingView
import com.example.coin.com.example.coin.Coin
import com.example.coin.com.example.coin.CoinView
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.activity_editor_color.*
import kotlinx.android.synthetic.main.activity_editor_corner.*
import kotlinx.android.synthetic.main.activity_editor_draw.*
import kotlinx.android.synthetic.main.coin_view_include.*
import java.util.*

class EditorActivity : AppCompatActivity() {

    var coin = Coin()
    lateinit var printBitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setEditorButtons()
        printBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)

    }

    private fun setEditorButtons() {
        // BUTTONS EDITOR //
        val btnColorMenu = findViewById<ImageButton>(R.id.btnColorMenu)
        btnColorMenu.setOnClickListener {
            setContentView(R.layout.activity_editor_color)
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            setColorButtons()

        }
        val btnCornerMenu = findViewById<ImageButton>(R.id.btnCornerMenu)
        btnCornerMenu.setOnClickListener {
            setContentView(R.layout.activity_editor_corner)
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            setCornerButtons()
        }
        val btnDrawMenu = findViewById<ImageButton>(R.id.btnDrawMenu)
        btnDrawMenu.setOnClickListener {
            setContentView(R.layout.activity_editor_draw)
            val drawingView : DrawingView = findViewById(R.id.drawingView)
            drawingView.drawColor = coin.strokeColor
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            setDrawButtons()
        }
        val resetBtn = findViewById<Button>(R.id.resetBtn)
        resetBtn.setOnClickListener {
            coin = Coin()
            printBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
        }
        val nextLevelBtn = findViewById<Button>(R.id.nextLevelBtn)
        nextLevelBtn.setOnClickListener {
            val intent = Intent(this, StressActivity::class.java).apply {
                putExtra("coin", coin)
            }
            startActivity(intent)
        }
        val nextToColorBtn = findViewById<Button>(R.id.nextToColorBtn)
        nextToColorBtn.setOnClickListener {
            setContentView(R.layout.activity_editor_color)
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            setColorButtons()
        }
    }

    private fun setColorButtons() {
        // BUTTONS COLOR //
        val changeColorBtn = findViewById<Button>(R.id.changeColorBtn)
        changeColorBtn.setOnClickListener {
            coin.setColor(randomColor())
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
        }
        val nextToCornerBtn = findViewById<Button>(R.id.nextToCornerBtn)
        nextToCornerBtn.setOnClickListener {
            setContentView(R.layout.activity_editor_corner)
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            setCornerButtons()
        }
    }

    private fun setCornerButtons() {

        val textView : TextView = findViewById(R.id.cornerCounterView)
        textView.text = coin.corners.toString()
        // BUTTONS CORNER //
        val moreCornersBtn = findViewById<Button>(R.id.moreCornersBtn)
        moreCornersBtn.setOnClickListener {
            coin.corners++
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            cornerCounterView.text = coin.corners.toString()
        }
        val lessCornersBtn = findViewById<Button>(R.id.lessCornersBtn)
        lessCornersBtn.setOnClickListener {
            coin.corners--
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            cornerCounterView.text = coin.corners.toString()
        }
        val nextToDrawBtn = findViewById<Button>(R.id.nextToDrawBtn)
        nextToDrawBtn.setOnClickListener {
            setContentView(R.layout.activity_editor_draw)
            val coinView : CoinView = findViewById(R.id.coinViewAll)

            val drawingView : DrawingView = findViewById(R.id.drawingView)
            drawingView.drawColor = coin.strokeColor
            coinView.update(coin, printBitmap)
            setDrawButtons()

        }
    }

    private fun setDrawButtons() {
        // BUTTONS DRAW //
        val updateDrawingBtn = findViewById<Button>(R.id.updateDrawingBtn)
        updateDrawingBtn.setOnClickListener {
            val coinView: CoinView = findViewById(R.id.coinViewAll)
            val drawingView : DrawingView = findViewById(R.id.drawingView)
            printBitmap = drawingView.extraBitmap
            coinView.update(coin, printBitmap)
            drawingView.cleanBitmap()
        }
        val nextToEditorBtn = findViewById<Button>(R.id.nextToEditorBtn)
        nextToEditorBtn.setOnClickListener {
            setContentView(R.layout.activity_editor)
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            setEditorButtons()

        }
    }

    // FUNCTIONS //
    fun randomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }



}