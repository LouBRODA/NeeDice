package com.example.needice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var playButton : Button
    private lateinit var settingsButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        playButton = findViewById(R.id.play_button)
        playButton.setOnClickListener {
            val playIntent = Intent(this, MainWindow::class.java)
            startActivity(playIntent)
        }

        settingsButton = findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }

}