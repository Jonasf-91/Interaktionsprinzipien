package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.quiz.Answer
import com.example.quiz.Question

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameActivityBtn = findViewById<Button>(R.id.gameActivityBtn)
        gameActivityBtn.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        val logInActivityBtn = findViewById<Button>(R.id.logInActivityBtn)
        logInActivityBtn.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        val optionActivityBtn = findViewById<Button>(R.id.optionActivityBtn)
        optionActivityBtn.setOnClickListener {
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }
        val editorActivityBtn = findViewById<Button>(R.id.editorActivityBtn)
        editorActivityBtn.setOnClickListener {
            val intent = Intent(this, EditorActivity::class.java)
            startActivity(intent)
        }
        val quizActivityBtn = findViewById<Button>(R.id.quizActivityBtn)
        quizActivityBtn.setOnClickListener {

            //Questions for Testing Activity
            val questions = arrayListOf<Question>()
            questions.add(
                Question(
                    "Gegen welche(s) Interaktionsprinzip(ien) wurde hier verstoßen?",
                    getString(R.string.answerQuiz01),
                    listOf(
                        Answer("A", true),
                        Answer("B", false),
                        Answer("C", true),
                        Answer("D", true),
                    ),
                    R.drawable.quiz01,
                    true
                ),
            )

            questions.add(
                Question(
                    "Gegen welches Interaktionsprinzip wurde hier verstoßen?",
                    getString(R.string.answerQuiz01),
                    listOf(
                        Answer("Steuerbarakeit", true),
                        Answer("Robust gegen Benutzerfehler", true),
                        Answer("Benutzerbindung", false),
                        Answer("Aufgabenangemessen", true),
                    ),
                    R.drawable.quiz02,
                false
                ),
            )


            val intent = Intent(this, QuizActivity::class.java).apply{
                putParcelableArrayListExtra("questions", questions)
            }
            startActivity(intent)
        }
    }
}