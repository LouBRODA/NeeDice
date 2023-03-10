package com.example.needice.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.needice.R
import com.example.needice.adaptor.ColorPalletAdaptor
import com.example.needice.model.Color

class MainWindow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_screen)
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = findViewById<RecyclerView>(R.id.list_color_display)
        recyclerView.adapter = ColorPalletAdaptor(ArrayList<Color>())
        recyclerView.layoutManager = LinearLayoutManager(this)
        //startActivityForResult(intent,0)
    }

}
