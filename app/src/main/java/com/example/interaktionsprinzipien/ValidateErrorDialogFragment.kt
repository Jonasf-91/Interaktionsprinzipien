package  com.example.interaktionsprinzipien

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_custom_dialog.view.*

class ValidateErrorDialogFragment : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.activity_custom_dialog, container, false)

        rootView.buttonCancelPopUp.setOnClickListener {
            val intent = Intent(context, StartActivity::class.java)
            startActivity(intent)
        }


        return  rootView
    }
}