package but.app.needice.view.holder

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import but.app.needice.R

class ColorPalletViewHolder(val cellule : View) : ViewHolder(cellule) {
    init{
        //cellule.findViewById<Button>(R.id.color_checked).setOnCheckedChangeListener { _, _ -> Log.i("TEST", "CHECKED") }
    }
}