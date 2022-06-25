package com.example.interaktionsprinzipien

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.example.quiz.Question
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_quiz_answers.*
import kotlinx.android.synthetic.main.activity_quiz_solution.*


class QuizActivity : AppCompatActivity() {

    private val quizButtons = mutableListOf<Button>()
    private var questions = arrayListOf<Question>()
    private var questionIndex = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // ------------- Load questions from intent extra -------------

        if (intent.getParcelableArrayListExtra<Question>("questions") != null) {
            questions = intent.getParcelableArrayListExtra<Question>("questions")!!
        }

        // -------------------------------------------------------------


        quizButtons.add(option1)
        quizButtons.add(option2)
        quizButtons.add(option3)
        quizButtons.add(option4)

        val itr = questions.iterator()
        var currentQuestion = getNextQuestion(itr)



        checkInputs.setOnClickListener {
            if (currentQuestion != null) {
                if (currentQuestion!!.isCorrect()){
                    quizAnswers.visibility = View.GONE
                    quizSolution.visibility = View.VISIBLE
                }else{
                    val dialog = QuizErrorDialogFragment()
                    dialog.show(supportFragmentManager, "customDialog")
                }
            }
        }

        nextQuestion.setOnClickListener {
            currentQuestion = getNextQuestion(itr)
            quizAnswers.visibility = View.VISIBLE
            quizSolution.visibility = View.GONE
        }



    }

    private fun getNextQuestion(questionsIterator : Iterator<Question>) : Question? {
        if (questionsIterator.hasNext())
            return nameLabelsAndButtons(questionsIterator.next())

        val intent = Intent(this, StressActivity::class.java)
        startActivity(intent)
        return null
    }

    private fun nameLabelsAndButtons(question : Question): Question {
        val answers = question.getAnswers()
        textViewQeuestion.text = question.getQuestionText()
        questionXY.text = "Frage " + questionIndex++ + " von " + questions.size

        textViewQuizSolution.text = HtmlCompat.fromHtml(question.getSolutionText(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        imageViewQuestion.setImageDrawable(ContextCompat.getDrawable(this, question.image))

        for ((index, button) in quizButtons.withIndex()){
            button.setBackgroundColor(Color.GRAY)
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
        return question;
    }

}