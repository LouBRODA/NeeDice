package com.example.needice

import android.R.attr.x
import android.R.attr.y
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainWindow : AppCompatActivity() {
    var sensorManager: SensorManager? = null
    var accelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_screen)
        rollDice()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    fun onSensorChanged(event: SensorEvent) {

        var x : Float
        var y : Float
        var z : Float

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]
        }
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
