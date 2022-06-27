package com.example.interaktionsprinzipien

import android.content.Intent
import android.graphics.Color
import com.example.coin.com.example.coin.CoinView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.coin.com.example.coin.Coin
import java.util.*


class EditorColorActivity : AppCompatActivity() {
    var coin = Coin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_color)


        if (intent.getParcelableExtra<Coin>("coin") != null) {
            coin = intent.getParcelableExtra("coin")!!
        }
        val coinView : CoinView = findViewById(R.id.coinViewEditor)
        coinView.update(coin)

        val changeColorBtn = findViewById<Button>(R.id.changeColorBtn)
        changeColorBtn.setOnClickListener {
            coin.setColor(randomColor())

            coinView.update(coin)
        }

        val nextBtn = findViewById<Button>(R.id.nextToCornerBtn)
        nextBtn.setOnClickListener {
            val intent = Intent(this, EditorCornerActivity::class.java).apply {
                putExtra("coin", coin)
            }
            startActivity(intent)
        }

    }

    fun randomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }


}