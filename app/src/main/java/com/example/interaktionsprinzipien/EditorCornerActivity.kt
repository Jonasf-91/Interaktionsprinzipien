package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.coin.com.example.coin.Coin
import com.example.coin.com.example.coin.CoinView


class EditorCornerActivity : AppCompatActivity() {

    var coin = Coin()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_corner)

        if (intent.getParcelableExtra<Coin>("coin") != null) {
            coin = intent.getParcelableExtra("coin")!!
        }
        val coinView : CoinView = findViewById(R.id.coinViewCorner)
        coinView.update(coin)

        val textView : TextView = findViewById(R.id.cornerCounterView)
        textView.text = coin.corners.toString()

        val moreCornersBtn = findViewById<Button>(R.id.moreCornersBtn)
        moreCornersBtn.setOnClickListener {
            coin.corners++
            coinView.update(coin)
            textView.text = coin.corners.toString()
        }

        val lessCornersBtn = findViewById<Button>(R.id.lessCornersBtn)
        lessCornersBtn.setOnClickListener {
            coin.corners--
            coinView.update(coin)
            textView.text = coin.corners.toString()
        }

        val nextBtn = findViewById<Button>(R.id.nextToDrawBtn)
        nextBtn.setOnClickListener {
            val intent = Intent(this, EditorDrawActivity::class.java).apply {
                putExtra("coin", coin)
            }
            startActivity(intent)
        }


    }
}