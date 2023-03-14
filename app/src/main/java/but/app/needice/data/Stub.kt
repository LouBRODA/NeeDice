package but.app.needice.data

import but.app.needice.model.Color
import but.app.needice.model.ColorPallet

class Stub {

    fun load(): ColorPallet {
        val dataColor = ColorPallet()

        dataColor.addColor(Color("#ffffff", "white"))
        dataColor.addColor(Color("#000000", "black"))

        return dataColor
    }
}