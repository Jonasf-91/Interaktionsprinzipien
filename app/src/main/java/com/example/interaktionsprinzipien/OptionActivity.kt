package com.example.interaktionsprinzipien

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class OptionActivity : AppCompatActivity() {

    var global = GlobalClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        val toggleMusic = findViewById<ToggleButton>(R.id.toggleMusic)
        val soundSwitch = findViewById<Switch>(R.id.soundSwitch)
        val helpSwitch = findViewById<Switch>(R.id.helpSwitch)
        val toggleSpeed = findViewById<ToggleButton>(R.id.toggleSpeed)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val forwardButton = findViewById<Button>(R.id.forwardButton)
        val infoMusic = findViewById<ImageView>(R.id.infoMusic)
        val infoCoins = findViewById<ImageView>(R.id.infoCoins)
        val infoSound = findViewById<ImageView>(R.id.infoSound)
        val infoHelp = findViewById<ImageView>(R.id.infoHelp)
        val infoGraphic = findViewById<ImageView>(R.id.infoGraphic)
        val infoSpeed = findViewById<ImageView>(R.id.infoSpeed)

        var saved = false

        toggleMusic.isChecked = true


        if(toggleMusic.isChecked){
            playSound()
        }


        toggleMusic.setOnClickListener {
            if(toggleMusic.isChecked){
                playSound()
            }
            else{
                stopSound()
            }
        }

        soundSwitch.setOnClickListener {
            if(soundSwitch.isChecked){
                //SOUNDEFFEKTE ANMACHEN
            }
            else{
                //SOUNDEFFEKTE AUSMACHEN
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

        infoSound.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Soundeffekte an- oder ausschalten", Toast.LENGTH_LONG).show()
            }
        }

        infoHelp.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Infobuttons aktivieren/deaktivieren", Toast.LENGTH_LONG).show()
            }
        }

        infoGraphic.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Displaygröße des Geräts in Pixel x Pixel angeben", Toast.LENGTH_LONG).show()
            }
        }

        infoSpeed.setOnClickListener {
            if (helpSwitch.isChecked){
                Toast.makeText(applicationContext, "Spielgeschwindigkeit des Computergegners", Toast.LENGTH_LONG).show()
            }
        }

        saveButton.setOnClickListener {
            saved = true
        }

        forwardButton.setOnClickListener {

            if (saved){
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)
            } else {
                val dialog = ValidateErrorDialogFragment()
                dialog.show(supportFragmentManager, "Sie müssen Ihre Optionen erst abspeichern!")
            }
        }
    }

    fun playSound() {
        if (global.player == null) {
            global.player = MediaPlayer.create(this, R.raw.water)
            global.player!!.isLooping = true
            global.player!!.start()
        }
        if(global.player?.isPlaying == false){
            global.player!!.start()
        }
    }

    fun pauseSound() {
        if (global.player?.isPlaying == true) global.player?.pause()
    }

    fun stopSound() {
        if (global.player != null) {
            global.player!!.stop()
            global.player!!.release()
            global.player = null
        }
    }

    /*override fun onStop() {
        super.onStop()
        if (global.player != null) {
            global.player!!.release()
            global.player = null
        }
    }*/

    private fun setCoins() {
        val coinAmount = findViewById<EditText>(R.id.coinAmount);

    }

}