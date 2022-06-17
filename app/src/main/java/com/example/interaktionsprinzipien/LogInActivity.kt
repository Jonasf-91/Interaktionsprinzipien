package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.util.*

class LogInActivity : AppCompatActivity() {

    enum class EditTarget {
        DAY, MONTH, YEAR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        setUpBirthday()
        setUpUsername()
        setUpEmail()

        val saveButton = findViewById<Button>(R.id.buttonSave)
        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val nextStepButton = findViewById<TextView>(R.id.nextStepButton)

        saveButton.setOnClickListener {
            Toast.makeText(applicationContext, "Alles wurde erfolgreich gespeichert!", Toast.LENGTH_LONG).show()
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        nextStepButton.setOnClickListener {
            val intent = Intent(this, StressActivity::class.java)
            startActivity(intent)
        }



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
