package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.coin.com.example.coin.Coin
import com.example.coin.com.example.coin.CoinView

class EditorActivity : AppCompatActivity() {

    var coin = Coin()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        if (intent.getParcelableExtra<Coin>("coin") != null) {
            coin = intent.getParcelableExtra("coin")!!
        }
        val coinView : CoinView = findViewById(R.id.coinViewEditor)
        coinView.update(coin)

        val btnColorMenu = findViewById<ImageButton>(R.id.btnColorMenu)
        btnColorMenu.setOnClickListener {
            val intent = Intent(this, EditorColorActivity::class.java).apply{
                putExtra("coin", coin)

            }
            startActivity(intent)
        }

        val btnCornerMenu = findViewById<ImageButton>(R.id.btnCornerMenu)
        btnCornerMenu.setOnClickListener {
            val intent = Intent(this, EditorCornerActivity::class.java).apply{
                putExtra("coin", coin)

            }
            startActivity(intent)
        }

        val btnDrawMenu = findViewById<ImageButton>(R.id.btnDrawMenu)
        btnDrawMenu.setOnClickListener {
            val intent = Intent(this, EditorDrawActivity::class.java).apply{
                putExtra("coin", coin)

            }
            startActivity(intent)
        }

        val resetBtn = findViewById<Button>(R.id.resetBtn)
        resetBtn.setOnClickListener {
            coin = Coin()
            coinView.update(coin)

        }

        val nextLevelBtn = findViewById<Button>(R.id.nextLevelBtn)
        nextLevelBtn.setOnClickListener {
            val intent = Intent(this, StressActivity::class.java).apply {
                putExtra("coin", coin)
            }
            startActivity(intent)
        }

        val nextBtn = findViewById<Button>(R.id.nextToColorBtn)
        nextBtn.setOnClickListener {
            val intent = Intent(this, EditorColorActivity::class.java).apply {
                putExtra("coin", coin)
            }
            startActivity(intent)
        }
    }




}