package com.example.quiz

import android.os.Parcel
import android.os.Parcelable
import android.view.textservice.TextInfo
import android.widget.EditText

class Question (private val questionText: String? = "", private val solutionText: String? = "", private val answers: List<Answer>?, val image : Int, val isSelfInput : Boolean = false) : Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Answer),
        parcel.readInt(),
        parcel.readBoolean(),
    ) {
    }



    public fun isCorrect() : Boolean {
        if (answers != null) {
            for (answer: Answer in answers){
                if (!answer.isSelectionCorrect()){
                    return false
                }
            }
        }
        return true
    }

    public fun isCorrect(textAnswers : List<EditText>) : Boolean {
        val textSolutions = mutableListOf<String>()
        for (answer in textAnswers){
            if (answer.text.toString() != "") {
                textSolutions.add(answer.text.toString().lowercase())
            }
        }

        if (answers != null) {
            for (answer: Answer in answers) {
                if (!answer.isSelectionCorrect(textSolutions)){
                    return false
                }
            }
        }
        return textSolutions.isEmpty()
    }

    public fun getSolutionText() : String {
        if (solutionText != null)
            return solutionText
        return  ""
    }

    public fun getQuestionText() : String {
        if (questionText != null)
            return questionText
        return ""
    }

    public fun getAnswers() : List<Answer> {
        if (answers != null)
            return answers
        return listOf()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(questionText)
        parcel.writeString(solutionText)
        parcel.writeTypedList(answers)
        parcel.writeInt(image)
        parcel.writeBoolean(isSelfInput)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }

}