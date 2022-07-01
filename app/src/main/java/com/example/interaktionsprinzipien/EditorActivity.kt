package com.example.interaktionsprinzipien

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coin.DrawingView
import com.example.coin.com.example.coin.Coin
import com.example.coin.com.example.coin.CoinView
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.*

class EditorActivity : AppCompatActivity() {

    var coin = Coin()
    lateinit var printBitmap : Bitmap
    var fileName : String? = ""
    val dangerColor = Color.RED
    val standardColor = Color.DKGRAY

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
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            fileName = createImageFromBitmap(coinView.coinBitmap)

            val intent = Intent(this, StressMeasureActivity::class.java)
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
        val lessCornersBtn = findViewById<Button>(R.id.lessCornersBtn)
        moreCornersBtn.setOnClickListener {
            coin.corners++
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            textView.textSize = 50f
            textView.setTextColor(standardColor)
            textView.text = coin.corners.toString()
            lessCornersBtn.setOnClickListener {
                textView.textSize = 20f
                textView.text = "Du wolltest doch mehr"
                textView.setTextColor(dangerColor)
            }
        }

        lessCornersBtn.setOnClickListener {
            val coinView : CoinView = findViewById(R.id.coinViewAll)
            coinView.update(coin, printBitmap)
            textView.textSize = 50f
            textView.text = coin.corners.toString()
            textView.setTextColor(standardColor)
            if(coin.corners>3){
                coin.corners--
            } else{
                textView.textSize = 20f
                textView.text = "Reicht jetzt auch mal!"
                textView.setTextColor(dangerColor)
            }

            moreCornersBtn.setOnClickListener {
                textView.textSize = 20f
                textView.text = "Du wolltest doch weniger"
                textView.setTextColor(dangerColor)
            }

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

    fun createImageFromBitmap(bitmap: Bitmap): String? {
        var fileName: String? = "myCoin" //no .png or .jpg needed
        try {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
            val fo: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            fo.write(bytes.toByteArray())
            // remember close file output
            fo.close()
        } catch (e: Exception) {
            e.printStackTrace()
            fileName = null
        }
        return fileName
    }



}