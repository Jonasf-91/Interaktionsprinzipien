package com.example.interaktionsprinzipien

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.Answer
import com.example.quiz.Question
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_option.*


class OptionActivity : AppCompatActivity() {

    private var global = GlobalClass()
    private var player: MediaPlayer? = null
    private var gameOptions = false
    private val questions = arrayListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

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
                R.drawable.quiz_options06
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
                R.drawable.quiz_options03)
        )



        gameOptions = false

        loadData()

        val toggleMusic = findViewById<ToggleButton>(R.id.toggleMusic)
        val volumeSwitch = findViewById<Switch>(R.id.volumeSwitch)
        val helpSwitch = findViewById<Switch>(R.id.helpSwitch)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val forwardButton = findViewById<Button>(R.id.forwardButton)
        val infoMusic = findViewById<ImageView>(R.id.infoMusic)
        val infoCoins = findViewById<ImageView>(R.id.infoCoins)
        val infoVolume = findViewById<ImageView>(R.id.infoVolume)
        val infoHelp = findViewById<ImageView>(R.id.infoHelp)
        val infoGraphic = findViewById<ImageView>(R.id.infoGraphic)
        val infoDifficulty = findViewById<ImageView>(R.id.infoSpeed)
        val infoFirstTurn = findViewById<ImageView>(R.id.infoFirstTurn)
        val displaySize = findViewById<TextView>(R.id.display)

        val difficultySeekBar = findViewById<SeekBar>(R.id.seekBarDifficulty)
        difficultySeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                var diff = difficultySeekBar.progress
                when (diff) {
                    1 -> difficultyText.setText("Leicht")
                    2 -> difficultyText.setText("Mittelschwer")
                    3 -> difficultyText.setText("Schwer")
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped

            }
        })



        var saved = false


        if(gameOptions){
            forwardButton.setText("Zurück")
        }
        else{
            forwardButton.setText("Weiter")
        }

        if(toggleMusic.isChecked && !volumeSwitch.isChecked){
            playMusic()
        }

        val displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels


        toggleMusic.setOnClickListener {
            if(toggleMusic.isChecked && !volumeSwitch.isChecked){
                playMusic()
            }
            else{
                pauseMusic()
            }
        }



        volumeSwitch.setOnClickListener {
            if(toggleMusic.isChecked && !volumeSwitch.isChecked){
                playMusic()
            }
            else{
                pauseMusic()
            }
        }

        infoMusic.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Musik an- oder ausschalten", Toast.LENGTH_LONG).show()
            }
        }

        infoCoins.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Anzahl der Münzen, die zum Spielen zur Verfügung stehen", Toast.LENGTH_LONG).show()
            }
        }

        infoVolume.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Lautstärke ein- oder ausschalten", Toast.LENGTH_LONG).show()
            }
        }

        infoHelp.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Infobuttons aktivieren/deaktivieren", Toast.LENGTH_LONG).show()
            }
        }

        infoGraphic.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Displaygröße des Geräts in Pixel x Pixel", Toast.LENGTH_LONG).show()
            }
        }

        infoFirstTurn.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Wollen Sie den ersten Zug machen?", Toast.LENGTH_LONG).show()
            }
        }

        infoDifficulty.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Schwierigkeitsgrad des Computergegners von 1-3", Toast.LENGTH_LONG).show()
            }
        }

        displaySize.setOnClickListener {
            Toast.makeText(applicationContext, height.toString() + " x " + width.toString(), Toast.LENGTH_LONG).show()
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            if(validResolution(height, width)){
                saved = true
                saveData()
            }else{
                Toast.makeText(applicationContext, "Geben Sie Ihre korrekte Displaygröße an!", Toast.LENGTH_LONG).show()
            }
        }

        forwardButton.setOnClickListener {
            if (saved && !gameOptions){
                stopMusic()
                val intent = Intent(this, QuizActivity::class.java).apply{
                    putParcelableArrayListExtra("questions", questions)
                }
                startActivity(intent)
            }
            else if (saved && gameOptions){
                val intent = Intent(this, GameActivity::class.java)
                stopMusic()
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext, "Sie müssen Ihre Optionen erst abspeichern!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDiffText(): String {
        var diff = 0
        var diffText = ""

        if(difficultyText.text.toString() != ""){
            diff = difficultyText.text.toString().toInt()
            if(diff >= 3){
                diff = 4
            }
            if(diff <= 1){
                diff = 0
            }
        }
        when (diff) {
            0 -> diffText = "EASY"
            2 -> diffText = "MEDIUM"
            4 -> diffText = "HARD"
        }
        return diffText
    }

    private fun saveData(){

        val resX = resolutionX.text.toString()
        val resY = resolutionY.text.toString()
        var diff = seekBarDifficulty.progress
        var coinAmt = 1

        if(coinAmount.text.toString() != ""){
            coinAmt = coinAmount.text.toString().toInt()
            if(coinAmt < 1){
                coinAmt = 1
            }
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("STRING_RESOLUTION_X", resX)
            putString("STRING_RESOLUTION_Y", resY)
            putBoolean("BOOLEAN_MUSIC", toggleMusic.isChecked)
            putBoolean("BOOLEAN_VOLUME", volumeSwitch.isChecked)
            putBoolean("BOOLEAN_HELP", helpSwitch.isChecked)
            putBoolean("BOOLEAN_FIRST_TURN", checkFirstTurn.isChecked)
            putBoolean("BOOLEAN_GAME_OPTIONS", gameOptions)
            putInt("INT_DIFFICULTY", diff)
            putInt("INT_COINS", coinAmt)
        }.apply()

        Toast.makeText(this, "Optionen gespeichert", Toast.LENGTH_LONG).show()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val stringResolutionX = sharedPreferences.getString("STRING_RESOLUTION_X", null)
        val stringResolutionY = sharedPreferences.getString("STRING_RESOLUTION_Y", null)
        val booleanMusic = sharedPreferences.getBoolean("BOOLEAN_MUSIC", false)
        val booleanVolume = sharedPreferences.getBoolean("BOOLEAN_VOLUME", true)
        val booleanHelp = sharedPreferences.getBoolean("BOOLEAN_HELP", false)
        val booleanFirstTurn = sharedPreferences.getBoolean("BOOLEAN_FIRST_TURN", false)
        val intDifficulty = sharedPreferences.getInt("INT_DIFFICULTY", 1)
        val intCoin = sharedPreferences.getInt("INT_COINS", 0)
        val booleanGameOptions = sharedPreferences.getBoolean("BOOLEAN_GAME_OPTIONS", false)


        resolutionX.setText(stringResolutionX)
        resolutionY.setText(stringResolutionY)
        toggleMusic.isChecked = booleanMusic
        volumeSwitch.isChecked = booleanVolume
        helpSwitch.isChecked = booleanHelp
        checkFirstTurn.isChecked = booleanFirstTurn
        seekBarDifficulty.progress = intDifficulty
        coinAmount.setText(intCoin.toString())
        gameOptions = booleanGameOptions

        var diff = seekBarDifficulty.progress
        when (diff) {
            1 -> difficultyText.setText("Leicht")
            2 -> difficultyText.setText("Mittelschwer")
            3 -> difficultyText.setText("Schwer")
        }
    }

    fun playMusic() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.monkeys_spinning_monkeys)
            player!!.isLooping = true
            player!!.start()
        }
        if(player?.isPlaying == false){
            player!!.start()
        }
    }

    fun pauseMusic() {
        if (player?.isPlaying == true) player?.pause()
    }


    fun stopMusic() {
        if (player != null && player?.isPlaying == true) {
            player!!.stop()
            player!!.release()
            player = null
        }
    }

    override fun onStop() {
        super.onStop()


        //HIER SOLLEN DIE PREFERENCES GELÖSCHT WERDEN, UM BEI NEUSTART DER APP KOMPLETT NEU ANFANGEN ZU KÖNNEN. FUNKTIONIERT NICHT!!!!!
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear()
        sharedPreferences.edit().apply()

        if (player != null) {
            player!!.release()
            player = null
        }
    }

    fun validResolution(height : Int, width : Int): Boolean {
        var valid = false
        if(findViewById<EditText>(R.id.resolutionX).text.toString() != "" && findViewById<EditText>(R.id.resolutionY).text.toString() != ""){
            val xRes = findViewById<EditText>(R.id.resolutionX).text.toString().toInt()
            val yRes = findViewById<EditText>(R.id.resolutionY).text.toString().toInt()

            if(xRes == height && yRes == width){
                valid = true
            }
        }
        return valid
    }
}