package but.app.needice.view

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import but.app.needice.R
import but.app.needice.adaptor.ColorPalletAdaptor
import but.app.needice.model.Color
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var listen : TextToSpeech? = null
    private lateinit var roll : Button
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var textX: TextView
    private lateinit var textY: TextView
    private lateinit var textZ: TextView
    private var canRoll: Boolean = true             //Permet de ne pas appeler le dé à l'infini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_screen)
        print("TEST")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        textX = findViewById(R.id.textx)
        textY = findViewById(R.id.texty)
        textZ = findViewById(R.id.textz)

        //roll = findViewById(R.id.roll_button)
        listen = TextToSpeech(this, this)
        rollDice()
    }

    override fun onInit(state: Int) {
        if (state == TextToSpeech.SUCCESS) {
            val result = listen!!.setLanguage(Locale.FRENCH)

            /*if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeak","ERR : Language not supported !")
            }else{
                roll.isEnabled = false
            }*/
        }
    }

    private fun speakOut(text : String) {
        listen!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = findViewById<RecyclerView>(R.id.list_color_display)
        recyclerView.adapter = ColorPalletAdaptor(ArrayList<Color>())
        recyclerView.layoutManager = LinearLayoutManager(this)

        sensorManager.registerListener(accelListener, sensor,               //Add notre capteur à la liste des capteurs "vivant" du sensorManager, permet donc l'écoute sur ce capteur
            SensorManager.SENSOR_DELAY_NORMAL)
        //startActivityForResult(intent,0)
    }
    override fun onStop() {
        super.onStop();
        sensorManager.unregisterListener(accelListener)       //Add notre capteur à la liste des capteurs "mort" du sensorManager, désactive donc le capteur
    }

    private fun rollDice(){
        val text : TextView = findViewById(R.id.number)
        val rd : Random = Random

        val de = 1 + rd.nextInt(7 - 1)
        text.text = de.toString()

        val form : View = findViewById(R.id.dice_form)
        val animator = AnimatorInflater.loadAnimator(this, R.animator.dice_animator)
        animator.setTarget(form)
        animator.start()
        speakOut(text.text.toString())

        Handler().postDelayed({             //Thread permettant de relancer le dé uniquement 1 fois par seconde
            canRoll = true
        }, 5000)
    }


    private fun checkValue(x : Float,y : Float,z : Float){
        if(x >=20.0 || x<=-20 || y>=20 || y<=10 || z<=-20 || z>=20){       //x.pow(2) --------> A regarder pour systeme plus propre //sensibilité moyenne
            canRoll = false
            rollDice()
        }
        else {
            canRoll = true
        }

    }

    @SuppressLint("SetTextI18n")        //Norme pour le text (comme UTF8)
    private var accelListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, acc: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]     //Axe x du capteur
            val y = event.values[1]     //Axe y du capteur
            val z = event.values[2]     //Axe z du capteur

            if(canRoll){
                checkValue(x,y,z)
            }
            textX.text = ("X : " + x.toInt())
            textY.text = ("Y : " + y.toInt())
            textZ.text = ("Z : " + z.toInt())
        }
    }
}
