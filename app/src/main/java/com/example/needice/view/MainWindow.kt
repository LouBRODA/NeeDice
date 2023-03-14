package com.example.needice.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.needice.R
import com.example.needice.adaptor.ColorPalletAdaptor
import com.example.needice.model.Color
import kotlin.random.Random

class MainWindow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_screen)
        rollDice()
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = findViewById<RecyclerView>(R.id.list_color_display)
        recyclerView.adapter = ColorPalletAdaptor(ArrayList<Color>())
        recyclerView.layoutManager = LinearLayoutManager(this)
        //startActivityForResult(intent,0)
    }

    private fun rollDice(){
        val roll : Button = findViewById(R.id.roll_button)
        val text : TextView = findViewById(R.id.number)
        val rd : Random = Random
        roll.setOnClickListener {
            val de = 1 + rd.nextInt(7 - 1)
            text.text = de.toString()
            print(text)
        }
        setContentView(R.layout.play_screen)
    }

}
