package com.example.needice

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainWindow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_screen)
        rollDice()
    }

    private fun rollDice(){
        val roll : Button = findViewById(R.id.rollButton)
        val text : TextView = findViewById(R.id.number)
        val rd : Random = Random
        roll.setOnClickListener {
            val de = 1 + rd.nextInt(7 - 1)
            text.text = de.toString()
            print(text)
        }
    }

}
