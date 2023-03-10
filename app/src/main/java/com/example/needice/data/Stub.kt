package com.example.needice.data

import com.example.needice.model.Color
import com.example.needice.model.ColorPallet

class Stub {

    fun load(): ColorPallet{
        val dataColor = ColorPallet()

        dataColor.addColor(Color("#ffffff", "white"))
        dataColor.addColor(Color("#000000", "black"))

        return dataColor
    }
}