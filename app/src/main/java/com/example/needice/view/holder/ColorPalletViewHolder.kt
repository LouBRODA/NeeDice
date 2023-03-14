package com.example.needice.view.holder

import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.needice.R

class ColorPalletViewHolder(val cellule : View) : ViewHolder(cellule) {
    init{
        cellule.findViewById<CheckBox>(R.id.color_checked).setOnCheckedChangeListener { _, _ -> Log.i("TEST", "CHECKED") }
    }
}