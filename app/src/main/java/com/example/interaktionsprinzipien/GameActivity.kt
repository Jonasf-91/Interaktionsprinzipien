package com.example.interaktionsprinzipien


import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity(){
    private lateinit var countDownTimer: CountDownTimer
    private var countDownProgress : Int = 0
    private var countDownStart : Int = 6


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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
}