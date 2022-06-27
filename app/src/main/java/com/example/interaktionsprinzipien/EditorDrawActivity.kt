package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.widget.Button
import com.example.coin.DrawingView
import com.example.coin.com.example.coin.Coin
import com.example.coin.com.example.coin.CoinView
import kotlinx.android.synthetic.main.activity_editor_draw.*
import kotlinx.android.synthetic.main.coin_view_include.*

class EditorDrawActivity : AppCompatActivity() {

    var coin = Coin()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_draw)

        if (intent.getParcelableExtra<Coin>("coin") != null) {
            coin = intent.getParcelableExtra("coin")!!
        }


        coinViewAll.update(coin)


        updateDrawingBtn.setOnClickListener {
            coinViewAll.updatePrint(drawingView.extraBitmap)
            drawingView.cleanBitmap()
        }

        val nextBtn = findViewById<Button>(R.id.nextToEditorBtn)
        nextBtn.setOnClickListener {
            val intent = Intent(this, EditorActivity::class.java).apply {
                putExtra("coin", coin)
            }
            startActivity(intent)
        }

    }
}