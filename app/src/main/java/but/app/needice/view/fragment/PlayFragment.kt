package but.app.needice.view.fragment

import android.Manifest.permission
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import but.app.needice.R
import but.app.needice.adaptor.ColorPalletAdaptor
import but.app.needice.model.Color
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    private var canRoll: Boolean = true
    private lateinit var dice : ImageView

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(permission.RECORD_AUDIO)

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


        // Vérification des permissions pour enregistrer l'audio
        ActivityCompat.requestPermissions(
            requireActivity(),
            permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )

        // Définition du listener de reconnaissance vocale
        recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {}
            override fun onResults(results: Bundle) {
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    for (match in matches) {
                        if (match.contains("yes")) {
                            rollDice()
                            break
                        }
                    }
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        val micro = view.findViewById<Button>(R.id.micro)
        micro.setOnClickListener {
            if (permissionToRecordAccepted) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(
                    RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    requireContext().packageName
                )
                speechRecognizer.startListening(intent)
                speechRecognizer.setRecognitionListener(recognitionListener)
            } else {
                Toast.makeText(context, "Permission to use microphone not granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        activateDrawer(view)
        buttonColor(view)
        return view
    }

    @Deprecated("Deprecated")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> {
                permissionToRecordAccepted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    override fun onInit(state: Int) {
        if (state == TextToSpeech.SUCCESS) {
            val prefs = requireActivity().getSharedPreferences("LanguageChosen", Context.MODE_PRIVATE)
            val language = prefs.getString("language", Locale.getDefault().language)
            val locale = language?.let { Locale(it) }
            val result = listen!!.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeak", "Language is not supported")
            }
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

        val buttonOpen = view.findViewById<Button>(R.id.button_left)
        buttonOpen.setOnClickListener {
            if (rightDrawer.visibility == View.GONE) {
                rightDrawer.visibility = View.VISIBLE
                leftDrawer.visibility = View.GONE
            }
        }

        val buttonClose = view.findViewById<Button>(R.id.button_right)
        buttonClose.setOnClickListener {
            if (leftDrawer.visibility == View.GONE) {
                rightDrawer.visibility = View.GONE
                leftDrawer.visibility = View.VISIBLE
            }
        }
    }

    private fun buttonColor(view: View){
        view.findViewById<Button>(R.id.button_red)?.setOnClickListener {
            val red = ContextCompat.getColor(requireContext(), R.color.red)
            changeColor(red)
        }

        view.findViewById<Button>(R.id.button_green)?.setOnClickListener {
            val green = ContextCompat.getColor(requireContext(), R.color.green)
            changeColor(green)
        }

        view.findViewById<Button>(R.id.button_black)?.setOnClickListener {
            val black = ContextCompat.getColor(requireContext(), R.color.black)
            changeColor(black)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun changeColor(color: Int){
        dice.setBackgroundColor(color)
    }
}