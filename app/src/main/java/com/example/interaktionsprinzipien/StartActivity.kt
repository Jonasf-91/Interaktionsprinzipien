package com.example.interaktionsprinzipien

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class StartActivity : AppCompatActivity() {

    var global = GlobalClass()
    var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val startButton = findViewById<Button>(R.id.startButton)
        val audioButton = findViewById<ImageButton>(R.id.audioButtonStart)

        playMusic()

        startButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        audioButton.setOnClickListener {
            if(true){
                playMusic()
                audioButton.setImageResource(R.drawable.audio_on)
            }
            else{
                pauseMusic()
                audioButton.setImageResource(R.drawable.audio_off)
            }
        }
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
}