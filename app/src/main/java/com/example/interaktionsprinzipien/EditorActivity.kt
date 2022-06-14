package com.example.interaktionsprinzipien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class EditorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        val btnColorMenu = findViewById<ImageButton>(R.id.btnColorMenu)
        btnColorMenu.setOnClickListener {
            val intent = Intent(this, EditorColorActivity::class.java)
            startActivity(intent)
        }
    }




}