package but.app.needice.view

import android.os.Bundle
import android.service.autofill.OnClickAction
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
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


class MainWindow : AppCompatActivity(), TextToSpeech.OnInitListener {

    var listen : TextToSpeech? = null
    lateinit var roll : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_screen)
        roll = findViewById(R.id.roll_button)
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
        //startActivityForResult(intent,0)
    }

    private fun rollDice(){
        val text : TextView = findViewById(R.id.number)
        val rd : Random = Random

        roll.setOnClickListener {
            val de = 1 + rd.nextInt(7 - 1)
            text.text = de.toString()
            print(text)

            val form : View = findViewById(R.id.dice_form);
            val animation = AnimationUtils.loadAnimation(this, R.anim.dice_animation)
            form.startAnimation(animation)
            speakOut(text.text.toString())
        }
    }



}
