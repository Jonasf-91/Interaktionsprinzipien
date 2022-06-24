package com.example.interaktionsprinzipien

import android.graphics.Color
import android.graphics.Color.red
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private val quizButtons = mutableListOf<Button>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        //---------- Define your Question and Answers here ----------
        val myAnswers = mutableListOf<Answer>();
        myAnswers.add(Answer("Aufgabenangemessen", true))
        myAnswers.add(Answer("Benutzerbindung", false))
        myAnswers.add(Answer("Steuerbarakeit", true))
        myAnswers.add(Answer("Robust gegen Benutzerfehler", true))

        val question = Question(
            "Gegen welche(s) Interaktionsprinzip(ien) wurde hier verstoßen?",
            myAnswers,
            R.drawable.quiz01
        )
        //------------------------------------------------------------

        quizButtons.add(option1)
        quizButtons.add(option2)
        quizButtons.add(option3)
        quizButtons.add(option4)

        nameLabelsAndButtons(question)


        checkInputs.setOnClickListener {
            if (question.isCorrect()){
                Toast.makeText(applicationContext, "Alles richtig!", Toast.LENGTH_LONG).show()
            }else{
                val dialog = QuizErrorDialogFragment()
                dialog.show(supportFragmentManager, "customDialog")
            }
        }

    }

    private fun nameLabelsAndButtons(question :Question){
        val answers = question.getAnswers()
        textViewQeuestion.text = question.getQuestionText()
        imageViewQuestion.setImageDrawable(ContextCompat.getDrawable(this, question.image))

        for ((index, button) in quizButtons.withIndex()){
            button.text = answers[index].name
            button.setOnClickListener {
                val current = answers[index].switchSelection()
                if (current){
                    button.setBackgroundColor(Color.rgb(75,181,67))
                }else{
                    button.setBackgroundColor(Color.GRAY)

                }
            }
        }
    }



}