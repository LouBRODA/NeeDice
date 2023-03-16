package but.app.needice.view

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import but.app.needice.R
import but.app.needice.adaptor.ColorPalletAdaptor
import but.app.needice.model.Color
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

            val form : View = findViewById(R.id.dice_form);
            val animation = AnimationUtils.loadAnimation(this, R.anim.dice_animation)
            form.startAnimation(animation)
        }
    }

}
