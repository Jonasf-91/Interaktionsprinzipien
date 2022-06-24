package com.example.interaktionsprinzipien

class Question (private val questionText: String = "", private val answers: List<Answer>, val image : Int) {
    public fun isCorrect() : Boolean {
        for (answer: Answer in answers){
            if (!answer.isSelectionCorrect()){
                return false
            }
        }
        return true
    }

    public fun getQuestionText() : String {
        return questionText
    }

    public fun getAnswers() : List<Answer> {
      return answers
    }

}

class Answer (val name: String, private val isCorrect: Boolean, private var isSelected: Boolean = false){
    public fun isSelectionCorrect() : Boolean {
        return isCorrect == isSelected
    }
    public fun switchSelection() : Boolean {
        isSelected = !isSelected
        return  isSelected
    }
}