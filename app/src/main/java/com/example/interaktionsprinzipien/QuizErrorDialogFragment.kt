package com.example.interaktionsprinzipien

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_qestion_false_dialog.view.*

class QuizErrorDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.activity_qestion_false_dialog, container, false)

        rootView.buttonGetSolutions.setOnClickListener {
            this.dismiss()
        }

        rootView.buttonBack.setOnClickListener {
            this.dismiss()
        }


        return  rootView
    }
}