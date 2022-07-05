package com.example.interaktionsprinzipien

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_option.*

class StartActivity : AppCompatActivity() {

    var global = GlobalClass()
    var player: MediaPlayer? = null
    var musicOn: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val startButton = findViewById<Button>(R.id.startButton)
        val audioButton = findViewById<ImageButton>(R.id.audioButtonStart)

        loadData()

        if(musicOn){
            playMusic()
        }
        else{
            audioButton.setImageResource(R.drawable.audio_off)
        }

        startButton.setOnClickListener {
            pauseMusic()
            saveData()
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        audioButton.setOnClickListener {
            if(musicOn){
                musicOn = false
                pauseMusic()
                audioButton.setImageResource(R.drawable.audio_off)
            }
            else{
                musicOn = true
                playMusic()
                audioButton.setImageResource(R.drawable.audio_on)
            }
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("BOOLEAN_MUSIC_START", musicOn)
            putBoolean("BOOLEAN_GAME_OPTIONS", false)
        }.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val booleanMusicStart = sharedPreferences.getBoolean("BOOLEAN_MUSIC_START", true)


        musicOn = booleanMusicStart
    }

    fun playMusic() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.marsel_minga)
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
}