package com.example.interaktionsprinzipien

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.StressScore


class StressMeasureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stress_measure)

        val score = findViewById<TextView>(R.id.textViewStressAmount)

        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                score.text = seek.progress.toString()
                            }
        })


        score.text = seekBar.progress.toString()

        val toQuizBtn = findViewById<Button>(R.id.toQuizBtn)
        toQuizBtn.setOnClickListener {

            StressScore.amount += seekBar.progress


            val intent = Intent(this, StressResultActivity::class.java)
            startActivity(intent)
        }


    }

}