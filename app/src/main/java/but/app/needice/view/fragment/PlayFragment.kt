package but.app.needice.view.fragment

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import but.app.needice.R
import but.app.needice.adaptor.ColorPalletAdaptor
import but.app.needice.model.Color
import java.util.*
import kotlin.random.Random


@Suppress("DEPRECATION")
class PlayFragment : Fragment(), TextToSpeech.OnInitListener {

    private var listen: TextToSpeech? = null
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var textX: TextView
    private lateinit var textY: TextView
    private lateinit var textZ: TextView
    private lateinit var leftDrawer: FrameLayout
    private lateinit var rightDrawer: FrameLayout
    private var canRoll: Boolean = true             //Permet de ne pas appeler le dé à l'infini
    private lateinit var dice : ImageView

    private val languages = listOf(
        Pair("English", "en"),
        Pair("French", "fr"),
        Pair("Spanish", "es")
    )

    //private val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages.map { it.first })
    //spinner.adapter = adapter

    @SuppressLint("CutPasteId", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.play_screen, container, false)

        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        textX = view.findViewById(R.id.textx)
        textY = view.findViewById(R.id.texty)
        textZ = view.findViewById(R.id.textz)

        dice = view.findViewById(R.id.dice_form)

        listen = TextToSpeech(context, this)

        activateDrawer(view)
        buttonColor(view)
        return view
    }

    override fun onInit(state: Int) {
        if (state == TextToSpeech.SUCCESS) {
            val result = listen!!.setLanguage(Locale.FRENCH)

            /*if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeak
            }else{
                roll.isEnabled = false
            }*/
        }
    }

    private fun speakOut(text: String) {
        listen!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.list_color_display)
        recyclerView?.adapter = ColorPalletAdaptor(ArrayList<Color>())
        recyclerView?.layoutManager = LinearLayoutManager(this.context)

        sensorManager.registerListener(
            accelListener,
            sensor,               //Add notre capteur à la liste des capteurs "vivant" du sensorManager, permet donc l'écoute sur ce capteur
            SensorManager.SENSOR_DELAY_NORMAL
        )
        //startActivityForResult(intent,0)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(accelListener)       //Add notre capteur à la liste des capteurs "mort" du sensorManager, désactive donc le capteur
    }

    private fun rollDice() {
        val text: TextView? = view?.findViewById(R.id.number)
        val rd: Random = Random

        val de = 1 + rd.nextInt(7 - 1)
        text?.text = de.toString()

        val form: View? = view?.findViewById(R.id.dice_form)
        val animator = AnimatorInflater.loadAnimator(this.context, R.animator.dice_animator)
        animator.setTarget(form)
        animator.start()
        speakOut(text?.text.toString())

        Handler().postDelayed({             //Thread permettant de relancer le dé uniquement 1 fois par seconde
            canRoll = true
        }, 2000)
    }

    private fun checkValue(x: Float, y: Float, z: Float) {
        if (x >= 20.0 || x <= -20 || y >= 20 || y <= -10 || z <= -20 || z >= 20) {       //x.pow(2) --------> A regarder pour systeme plus propre //sensibilité moyenne
            canRoll = false
            rollDice()
        } else {
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

            if (canRoll) {
                checkValue(x, y, z)
            }
            textX.text = ("X : " + x.toInt())
            textY.text = ("Y : " + y.toInt())
            textZ.text = ("Z : " + z.toInt())
        }
    }

    private fun activateDrawer(view: View) {

        leftDrawer = view.findViewById(R.id.left_drawer)
        rightDrawer = view.findViewById(R.id.right_drawer)

        view.findViewById<Button>(R.id.button_left)?.setOnClickListener {
            leftDrawer.visibility = View.VISIBLE
        }
        view.findViewById<Button>(R.id.button_left2)?.setOnClickListener {
            leftDrawer.visibility = View.INVISIBLE
        }

        view.findViewById<Button>(R.id.button_right)?.setOnClickListener {
            rightDrawer.visibility = View.VISIBLE
        }
        view.findViewById<Button>(R.id.button_right2)?.setOnClickListener {
            rightDrawer.visibility = View.INVISIBLE
        }
    }

    private fun buttonColor(view: View){
        view.findViewById<Button>(R.id.button_red)?.setOnClickListener {
            changeColor(R.color.red)
        }

        view.findViewById<Button>(R.id.button_green)?.setOnClickListener {
            changeColor(R.color.green)
        }

        view.findViewById<Button>(R.id.button_black)?.setOnClickListener {
            changeColor(R.color.black)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun changeColor(color: Int){
        dice.background.clearColorFilter()
        dice.background.setColorFilter(color, PorterDuff.Mode.SRC_OVER)
    }
}