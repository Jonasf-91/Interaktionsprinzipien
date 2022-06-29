package com.example.interaktionsprinzipien

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_option.*


class OptionActivity : AppCompatActivity() {

    var global = GlobalClass()
    var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        loadData()


        val toggleMusic = findViewById<ToggleButton>(R.id.toggleMusic)
        val volumeSwitch = findViewById<Switch>(R.id.volumeSwitch)
        val helpSwitch = findViewById<Switch>(R.id.helpSwitch)
        val toggleSpeed = findViewById<ToggleButton>(R.id.toggleSpeed)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val forwardButton = findViewById<Button>(R.id.forwardButton)
        val infoMusic = findViewById<ImageView>(R.id.infoMusic)
        val infoCoins = findViewById<ImageView>(R.id.infoCoins)
        val infoVolume = findViewById<ImageView>(R.id.infoVolume)
        val infoHelp = findViewById<ImageView>(R.id.infoHelp)
        val infoGraphic = findViewById<ImageView>(R.id.infoGraphic)
        val infoSpeed = findViewById<ImageView>(R.id.infoSpeed)
        val displaySize = findViewById<TextView>(R.id.display)

        var saved = false


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
            if(volumeSwitch.isChecked){
                //SOUNDEFFEKTE AUSMACHEN
            }
            else{
                //SOUNDEFFEKTE ANMACHEN
            }
        }


        toggleSpeed.setOnClickListener {
            if(toggleSpeed.isChecked){
                //SCHNELLES SPIEL EINSTELLEN)
            }
            else{
                //LANGSAMES SPIEL EINSTELLEN
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

        infoSpeed.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Spielgeschwindigkeit des Computergegners", Toast.LENGTH_LONG).show()
            }
        }

        displaySize.setOnClickListener {
            Toast.makeText(applicationContext, height.toString() + " x " + width.toString(), Toast.LENGTH_LONG).show()
        }

        saveButton.setOnClickListener {
            if(validResolution(height, width)){
                saved = true
                saveData()
            }else{
                Toast.makeText(applicationContext, "Geben Sie bitte die korrekte Displaygröße an", Toast.LENGTH_LONG).show()
            }
        }

        forwardButton.setOnClickListener {

            if (saved){
                val intent = Intent(this, QuizActivity::class.java)
                stopMusic()
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Sie müssen Ihre Optionen erst abspeichern!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveData(){

        val coinAmt = coinAmount.text.toString()
        val resX = resolutionX.text.toString()
        val resY = resolutionY.text.toString()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("STRING_COINS", coinAmt)
            putString("STRING_RESOLUTION_X", resX)
            putString("STRING_RESOLUTION_Y", resY)
            putBoolean("BOOLEAN_MUSIC", toggleMusic.isChecked)
            putBoolean("BOOLEAN_SPEED", toggleSpeed.isChecked)
            putBoolean("BOOLEAN_VOLUME", volumeSwitch.isChecked)
            putBoolean("BOOLEAN_HELP", helpSwitch.isChecked)
        }.apply()

        Toast.makeText(this, "Optionen gespeichert", Toast.LENGTH_LONG).show()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val stringCoin = sharedPreferences.getString("STRING_COINS", null)
        val stringResolutionX = sharedPreferences.getString("STRING_RESOLUTION_X", null)
        val stringResolutionY = sharedPreferences.getString("STRING_RESOLUTION_Y", null)
        val booleanMusic = sharedPreferences.getBoolean("BOOLEAN_MUSIC", false)
        val booleanSpeed= sharedPreferences.getBoolean("BOOLEAN_SPEED", false)
        val booleanVolume = sharedPreferences.getBoolean("BOOLEAN_VOLUME", true)
        val booleanHelp = sharedPreferences.getBoolean("BOOLEAN_HELP", false)


        coinAmount.setText(stringCoin)
        resolutionX.setText(stringResolutionX)
        resolutionY.setText(stringResolutionY)
        toggleMusic.isChecked = booleanMusic
        toggleSpeed.isChecked = booleanSpeed
        volumeSwitch.isChecked = booleanVolume
        helpSwitch.isChecked = booleanHelp
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

    fun setCoins() {
        coinAmount
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