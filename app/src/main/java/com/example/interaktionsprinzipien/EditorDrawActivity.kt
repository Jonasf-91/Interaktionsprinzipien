package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.coin.Coin
import com.example.coin.CoinView

class EditorDrawActivity : AppCompatActivity() {

    var coin = Coin()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_draw)

        if (intent.getParcelableExtra<Coin>("coin") != null) {
            coin = intent.getParcelableExtra("coin")!!
        }
        val coinView : CoinView = findViewById(R.id.coinViewDraw)
        coinView.update(coin)

        val nextBtn = findViewById<Button>(R.id.nextToEditorBtn)
        nextBtn.setOnClickListener {
            val intent = Intent(this, EditorActivity::class.java).apply {
                putExtra("coin", coin)
            }
            startActivity(intent)
        }
    }
}