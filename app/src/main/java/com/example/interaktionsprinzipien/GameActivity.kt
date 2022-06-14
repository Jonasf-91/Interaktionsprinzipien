package com.example.interaktionsprinzipien


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game_four_connect_content.*


class GameActivity : AppCompatActivity(){
    private lateinit var countDownTimer: CountDownTimer
    private var countDownProgress : Int = 0
    private var countDownStart : Int = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        four_connect_btn_options.setOnClickListener {
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }

        //starts OnClickListener for all arrowButtons
        setUpArrowButtons()

        //starts Countdown for the time above the playboard
        startCountdown()

    }

    private fun startCountdown(){
        countDownTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                countDownProgress ++
                tv_countdown.text = (countDownStart - countDownProgress).toString()
                //TODO add sound ticker
            }

            override fun onFinish() {
                //TODO get text from string.xml instead of hard-coding
                tv_countdown.text = "Entscheide Dich!"
                //TODO add sound highlight
            }
        }.start()
    }

    private fun setUpArrowButtons(){
        ib_four_connect_arrow0.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow0.setImageResource(R.drawable.four_connect_coin_test)
        }
        ib_four_connect_arrow1.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow1.setImageResource(R.drawable.four_connect_coin_test)
        }
        ib_four_connect_arrow2.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow2.setImageResource(R.drawable.four_connect_coin_test)
        }
        ib_four_connect_arrow3.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow3.setImageResource(R.drawable.four_connect_coin_test)
        }
        ib_four_connect_arrow4.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow4.setImageResource(R.drawable.four_connect_coin_test)
        }
        ib_four_connect_arrow5.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow5.setImageResource(R.drawable.four_connect_coin_test)
        }
        ib_four_connect_arrow6.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow6.setImageResource(R.drawable.four_connect_coin_test)
        }
    }

    private fun resetArrows(){
        ib_four_connect_arrow0.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow1.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow2.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow3.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow4.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow5.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow6.setImageResource(R.drawable.four_connect_arrow)
    }
}