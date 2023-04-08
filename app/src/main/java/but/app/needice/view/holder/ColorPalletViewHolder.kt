package but.app.needice.view.holder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import but.app.needice.R

class ColorPalletViewHolder(cellule : View) : ViewHolder(cellule) {
    val buttonColor : Button
    init{
        buttonColor = cellule.findViewById(R.id.button_color)
    }
}