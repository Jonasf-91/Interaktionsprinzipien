package com.example.interaktionsprinzipien

import android.graphics.Color
import android.graphics.Color.red
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    val options: MutableMap<Button, Boolean> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        options[option1] = false;
        options[option2] = false;
        options[option3] = false;
        options[option4] = false;

        option1.setOnClickListener {
            switchOption(option1);
        }

        option2.setOnClickListener {
            switchOption(option2);
        }

        option3.setOnClickListener {
            switchOption(option3);
        }

        option4.setOnClickListener {
            switchOption(option4);
        }

        checkInputs.setOnClickListener {
            if (check(first = true, second = false, third = false, fourth = true)){
                Toast.makeText(applicationContext, "Alles richtig!", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext, "Da ist was noch nichr richtig!", Toast.LENGTH_LONG).show()
            }
        }

        }

    private fun switchOption(button: Button) {
        if (options[button] == true) {
            button.setBackgroundColor(Color.GRAY);
            options[button] = false;
        }else {
            button.setBackgroundColor(Color.rgb(75,181,67));
            options[button] = true;
        }
    }

    private fun check(first:Boolean, second:Boolean, third:Boolean, fourth:Boolean): Boolean {
        if(options[option1] == first && options[option2] == second && options[option3] == third && options[option4] == fourth){
            return true;
        }
        return false;
    }
}