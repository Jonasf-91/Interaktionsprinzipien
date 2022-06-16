package com.example.interaktionsprinzipien

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stress.*


class StressActivity : AppCompatActivity() {

    private var progr = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stress)

        updateProgressBar()

        //val button_incr = findViewById<Button>(R.id.button_incr)
        button_incr.setOnClickListener {
            if (progr <= 90) {
                progr += 10
                updateProgressBar()
            }
        }

        //val button_decr = findViewById<Button>(R.id.button_decr)
        button_decr.setOnClickListener {
            if (progr >= 10) {
                progr -= 10
                updateProgressBar()
            }
        }
    }

    private fun updateProgressBar() {
        progress_bar.progress = progr
        text_view_progress.text = progr.toString()

        if (progr<=25)
            editTextTextMultiLine.setText("@string/result25")
        else if (progr<=50)
            editTextTextMultiLine.setText("@string/result50")
        else if (progr<=75)
            editTextTextMultiLine.setText("@string/result75")
        else if (progr<=100)
            editTextTextMultiLine.setText("@string/result100")

    }

}