package com.example.interaktionsprinzipien

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.StressScore
import kotlinx.android.synthetic.main.activity_stress_result.*
import java.io.File


class StressResultActivity : AppCompatActivity() {

    private var progr = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stress_result)


        progr = StressScore.amount
        updateProgressBar()

        //val fileName = intent.getStringExtra("myCoin")!!
        //val filePath: File = getFileStreamPath(fileName)
        //val d = Drawable.createFromPath(filePath.toString())
        //imageViewTest.setBackground(d)


    }

    private fun updateProgressBar() {
        val percentage = (progr * 100) / 40
        progress_bar.progress = percentage
        text_view_progress.text = progr.toString()

        if (progr<=25)
            editTextTextMultiLine.setText(resources.getString(R.string.result25))
        else if (progr<=50)
            editTextTextMultiLine.setText(getResources().getString(R.string.result50))
        else if (progr<=75)
            editTextTextMultiLine.setText(getResources().getString(R.string.result75))
        else if (progr<=100)
            editTextTextMultiLine.setText(getResources().getString(R.string.result100))

    }

}