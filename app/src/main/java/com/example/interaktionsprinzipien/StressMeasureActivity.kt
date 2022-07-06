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
        StressScore.level++

        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                score.text = seek.progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })


        score.text = seekBar.progress.toString()

        val nextBtn = findViewById<Button>(R.id.nextBtn)
        nextBtn.setOnClickListener {

            StressScore.amount += seekBar.progress


            when(StressScore.level){
                1 -> intent = Intent(this, EditorActivity::class.java)
                2 -> intent = Intent(this, OptionActivity::class.java)
                3 -> intent = Intent(this, GameActivity::class.java)
                4 -> intent = Intent(this, StressResultActivity::class.java)
                else -> intent = Intent(this, StressResultActivity::class.java)
            }
            startActivity(intent)
        }


    }

}