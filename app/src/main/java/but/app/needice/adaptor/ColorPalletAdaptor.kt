package but.app.needice.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import but.app.needice.R
import but.app.needice.data.entity.ColorEntity
import but.app.needice.model.Color
import but.app.needice.view.holder.ColorPalletViewHolder

class ColorPalletAdaptor(private val listColor : List<ColorEntity>) : RecyclerView.Adapter<ColorPalletViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPalletViewHolder {
        val cell = LayoutInflater.from(parent.context).inflate(R.layout.cellule_color, parent, false)
        return ColorPalletViewHolder(cell)
    }

    override fun onBindViewHolder(holder: ColorPalletViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.text_color_name).text = String.format("%s", listColor[position].name)
    }

    override fun getItemCount(): Int {
        return listColor.size
    }

}