package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameActivityBtn = findViewById<Button>(R.id.gameActivityBtn)
        gameActivityBtn.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        val logInActivityBtn = findViewById<Button>(R.id.logInActivityBtn)
        logInActivityBtn.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        val optionActivityBtn = findViewById<Button>(R.id.optionActivityBtn)
        optionActivityBtn.setOnClickListener {
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }
        val editorActivityBtn = findViewById<Button>(R.id.editorActivityBtn)
        editorActivityBtn.setOnClickListener {
            val intent = Intent(this, EditorActivity::class.java)
            startActivity(intent)
        }
    }
}