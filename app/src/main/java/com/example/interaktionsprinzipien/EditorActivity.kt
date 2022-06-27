package com.example.interaktionsprinzipien

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.coin.com.example.coin.Coin
import com.example.coin.com.example.coin.CoinView
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.activity_editor_color.*
import kotlinx.android.synthetic.main.activity_editor_corner.*
import kotlinx.android.synthetic.main.activity_editor_draw.*
import java.util.*

class EditorActivity : AppCompatActivity() {

    var coin = Coin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        val coinView : CoinView = findViewById(R.id.coinViewEditor)
        coinView.update(coin)

        setEditorButtons()
        //setColorButtons()
        //setCornerButtons()
        //setDrawButtons()

    }

    private fun setEditorButtons() {
        // BUTTONS EDITOR //
        val btnColorMenu = findViewById<ImageButton>(R.id.btnColorMenu)
        btnColorMenu.setOnClickListener {
            setContentView(R.layout.activity_editor_color)
            setColorButtons()
            val coinView : CoinView = findViewById(R.id.coinViewColor)
            coinView.update(coin)
        }
        val btnCornerMenu = findViewById<ImageButton>(R.id.btnCornerMenu)
        btnCornerMenu.setOnClickListener {
            setContentView(R.layout.activity_editor_corner)
            setCornerButtons()
            val coinView : CoinView = findViewById(R.id.coinViewCorner)
            coinView.update(coin)
        }
        val btnDrawMenu = findViewById<ImageButton>(R.id.btnDrawMenu)
        btnDrawMenu.setOnClickListener {
            setContentView(R.layout.activity_editor_draw)
            setDrawButtons()
            val coinView : CoinView = findViewById(R.id.coinViewDraw)
            coinView.update(coin)
        }
        val resetBtn = findViewById<Button>(R.id.resetBtn)
        resetBtn.setOnClickListener {
            coin = Coin()
            val coinView : CoinView = findViewById(R.id.coinViewEditor)
            coinView.update(coin)
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
            setColorButtons()
            val coinView : CoinView = findViewById(R.id.coinViewColor)
            coinView.update(coin)
        }
    }

    private fun setDrawButtons() {
        // BUTTONS DRAW //
        val updateDrawingBtn = findViewById<Button>(R.id.updateDrawingBtn)
        updateDrawingBtn.setOnClickListener {
            val coinView: CoinView = findViewById(R.id.coinViewDraw)
            coinView.updatePrint(drawingView.extraBitmap)
            drawingView.cleanBitmap()
        }
        val nextToEditorBtn = findViewById<Button>(R.id.nextToEditorBtn)
        nextToEditorBtn.setOnClickListener {
            setContentView(R.layout.activity_editor)
            setEditorButtons()
            val coinView : CoinView = findViewById(R.id.coinViewEditor)
            coinView.update(coin)
        }
    }

    private fun setCornerButtons() {
        // BUTTONS CORNER //
        val moreCornersBtn = findViewById<Button>(R.id.moreCornersBtn)
        moreCornersBtn.setOnClickListener {
            coin.corners++
            val coinView : CoinView = findViewById(R.id.coinViewCorner)
            coinView.update(coin)
            cornerCounterView.text = coin.corners.toString()
        }
        val lessCornersBtn = findViewById<Button>(R.id.lessCornersBtn)
        lessCornersBtn.setOnClickListener {
            coin.corners--
            val coinView : CoinView = findViewById(R.id.coinViewCorner)
            coinView.update(coin)
            cornerCounterView.text = coin.corners.toString()
        }
        val nextToDrawBtn = findViewById<Button>(R.id.nextToDrawBtn)
        nextToDrawBtn.setOnClickListener {
            setContentView(R.layout.activity_editor_draw)
            setDrawButtons()
            val coinView : CoinView = findViewById(R.id.coinViewDraw)
            coinView.update(coin)
        }
    }

    private fun setColorButtons() {
        // BUTTONS COLOR //
        val changeColorBtn = findViewById<Button>(R.id.changeColorBtn)
        changeColorBtn.setOnClickListener {
            coin.setColor(randomColor())
            val coinView : CoinView = findViewById(R.id.coinViewColor)
            coinView.update(coin)
        }
        val nextToCornerBtn = findViewById<Button>(R.id.nextToCornerBtn)
        nextToCornerBtn.setOnClickListener {
            setContentView(R.layout.activity_editor_corner)
            setCornerButtons()
            val coinView : CoinView = findViewById(R.id.coinViewCorner)
            coinView.update(coin)
        }
    }

    // FUNCTIONS //
    fun randomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }



}