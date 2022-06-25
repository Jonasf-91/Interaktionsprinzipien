package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.quiz.Answer
import com.example.quiz.Question
import java.time.LocalDate

class LogInActivity : AppCompatActivity() {

    enum class EditTarget {
        DAY, MONTH, YEAR
    }
    private val questions = arrayListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // ------------- Define Questions for Quiz -----------------
        questions.add(
            Question(
            "Gegen welche(s) Interaktionsprinzip(ien) wurde hier verstoßen?",
            getString(R.string.answerQuiz01),
            listOf(
                Answer("Aufgabenangemessen", true),
                Answer("Benutzerbindung", false),
                Answer("Steuerbarakeit", true),
                Answer("Robust gegen Benutzerfehler", true),
            ),
            R.drawable.quiz01
        )
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
            R.drawable.quiz02)
        )

        // --------------------------------------------------------

        setUpBirthday()
        setUpUsername()
        setUpEmail()

        var allInputsValidated = false;

        val saveButton = findViewById<Button>(R.id.buttonSave)
        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val nextStepButton = findViewById<TextView>(R.id.nextStepButton)

        saveButton.setOnClickListener {
            if (checkAllInputs()){
                allInputsValidated = true;
                Toast.makeText(applicationContext, "Alles wurde erfolgreich gespeichert!", Toast.LENGTH_LONG).show()
            }else {
                allInputsValidated = false;
                Toast.makeText(applicationContext, "Fehler bei deinen Eingaben, bitte beheben!", Toast.LENGTH_LONG).show()
            }
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        nextStepButton.setOnClickListener {

            if (allInputsValidated){
                val intent = Intent(this, QuizActivity::class.java).apply{
                    putParcelableArrayListExtra("questions", questions)
                }
                startActivity(intent)

            } else {
                val dialog = ValidateErrorDialogFragment()
                dialog.show(supportFragmentManager, "customDialog")
            }
        }
    }

    private fun checkAllInputs(): Boolean {
        val firstLastNameInput = findViewById<EditText>(R.id.firstLastNameField);
        val firstLastName = firstLastNameInput.text.toString();
        if (firstLastName == ""){
            return false;
        }

        val firstPartOfEmailInput = findViewById<EditText>(R.id.emailFirstPart);
        val secondPartOfEmailInput = findViewById<EditText>(R.id.emailSecondPart);
        val thirdPartOfEmailInput = findViewById<EditText>(R.id.emailTopLevelDomain);

        if (firstPartOfEmailInput.text.toString() == "" || secondPartOfEmailInput.text.toString() == ""){
            return false
        }
        val email = firstPartOfEmailInput.text.toString() + "@" + secondPartOfEmailInput.text.toString() +  "." +thirdPartOfEmailInput.text.toString();

        // Email senden :)

        val checkboxNextStep = findViewById<CheckBox>(R.id.checkBoxNextStep);
        val checkBoxCancel = findViewById<CheckBox>(R.id.checkBoxCancel);

        if(checkBoxCancel.isChecked || !checkboxNextStep.isChecked){
            return false
        }

        return true;
    }

    private fun setUpUsername(){
        val userName = findViewById<EditText>(R.id.userNameField)
        val userNames = setOf<String>("shaquille.oatmeal", "hanging_with_my_gnomies", "hoosier-daddy", "fast_and_the_curious", "averagestudent", "BadKarma", "google_was_my_idea", "cute.as.ducks", "casanova", "real_name_hidden", "HairyPoppins", "fedora_the_explorer", "OP_rah", "YellowSnowman", "Joe Not Exotic", "username_copied", "whos_ur_buddha", "unfinished_sentenc", "AllGoodNamesRGone", "Something", "me_for_president", "tinfoilhat", "oprahwindfury", "anonymouse", "Definitely_not_an_athlete", "HeartTicker", "YESIMFUNNY", "BenAfleckIsAnOkActor", "magicschoolbusdropout", "Everybody", "regina_phalange", "PawneeGoddess", "pluralizes_everythings", "chickenriceandbeans", "test_name_please_ignore", "IYELLALOT", "heyyou", "laugh_till_u_pee", "aDistraction", "crazy_cat_lady", "banana_hammock", "thegodfatherpart4", "unfriendme", "babydoodles", "fluffycookie", "buh-buh-bacon", "ashley_said_what", "LactoseTheIntolerant", "ManEatsPants", "Twentyfourhourpharmacy", "applebottomjeans", "Babushka", "toastedbagelwithcreamcheese", "baeconandeggz", "FartinLutherKing", "coolshirtbra", "kentuckycriedfricken", "REVERANDTOAST", "kim_chi", "idrinkchocolatemilk", "SaintBroseph", "chin_chillin", "ghostfacegangsta", "bigfootisreal", "santas_number1_elf", "thehornoftheunicorn", "iNeed2p", "abductedbyaliens", "actuallynotchrishemsworth", "nachocheesefries", "personallyvictimizedbyreginageorge", "just-a-harmless-potato", "FrostedCupcake", "Avocad", "fatBatman", "quailandduckeggs", "PaniniHead", "mandymooressingingvoice", "catsordogs", "FartnRoses", "RedMonkeyButt", "FreddyMercurysCat", "MasterCheif", "FreeHugz", "ima.robot", "actuallythedog", "notthetigerking", "pixie_dust", "ChopSuey", "turkey_sandwich", "B.Juice", "Chris_P_Bacon", "LtDansLegs", "WookiesrPpl2", "hogwartsfailure", "CourtesyFlush", "MomsSpaghetti", "spongebobspineapple", "garythesnai")

        userName.setText(userNames.random())

        userName.setOnClickListener{
            userName.setText(userNames.random())
        }
    }

    private fun setUpEmail(){
        val emailTopLevelDomain = findViewById<EditText>(R.id.emailTopLevelDomain)
        val topLevelDomains = setOf<String>("de", "at", "fr", "com", "nl", "ch", "net", "other")

        emailTopLevelDomain.setOnClickListener{
            emailTopLevelDomain.setText(topLevelDomains.random())
        }

    }

    private fun setUpBirthday(){
        val birthdayIncrementButton = findViewById<Button>(R.id.birthdayIncrementButton)
        val birthdayDay = findViewById<EditText>(R.id.birthdayDayField)
        val birthdayMonth = findViewById<EditText>(R.id.birthdayMonthField)
        val birthdayYear = findViewById<EditText>(R.id.birthdayYearField)
        var toEdit = EditTarget.DAY


        birthdayIncrementButton.setOnClickListener{
            val day = Integer.valueOf(birthdayDay.getText().toString())
            val month =  Integer.valueOf(birthdayMonth.getText().toString())
            val year =  Integer.valueOf(birthdayYear.getText().toString())

            var date = LocalDate.of(year,month,day)

            date = when (toEdit) {
                EditTarget.DAY -> date.plusDays(1)
                EditTarget.MONTH -> date.plusMonths(1)
                EditTarget.YEAR -> date.plusYears(1)
            }

            birthdayDay.setText(if(date.dayOfMonth < 10) "0" + date.dayOfMonth.toString() else date.dayOfMonth.toString())
            birthdayMonth.setText(if(date.monthValue < 10) "0" + date.monthValue.toString() else date.monthValue.toString())
            birthdayYear.setText(date.year.toString())
        }

        birthdayDay.setOnClickListener{
            toEdit = EditTarget.DAY
        }
        birthdayMonth.setOnClickListener{
            toEdit = EditTarget.MONTH
        }
        birthdayYear.setOnClickListener{
            toEdit = EditTarget.YEAR
        }
    }
}
