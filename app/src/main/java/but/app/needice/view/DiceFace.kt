package but.app.needice.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

    //--CLASS GENERATED NUMBER OF DOT--//
//*done with tuto help* (adapted for personal usage)//

class DiceFace(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val dotPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private var numDots = 0 // Nombre de points à afficher

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2 // Centre horizontal
        val centerY = height / 2 // Centre vertical

        // Rayon et espacement des points en fonction de la taille de la vue
        val radius = width / 4
        val spacing = radius / 1
        val dotRadius = radius / 2

        // Coordonnées des points sur un dé à six faces
        val pointCoords = when (numDots) {
            1 -> arrayOf(floatArrayOf(centerX, centerY))
            2 -> arrayOf(floatArrayOf(centerX - spacing, centerY), floatArrayOf(centerX + spacing, centerY))
            3 -> arrayOf(
                floatArrayOf(centerX - spacing, centerY - spacing),
                floatArrayOf(centerX + spacing, centerY + spacing),
                floatArrayOf(centerX, centerY)
            )
            4 -> arrayOf(
                floatArrayOf(centerX - spacing, centerY - spacing),
                floatArrayOf(centerX + spacing, centerY + spacing),
                floatArrayOf(centerX + spacing, centerY - spacing),
                floatArrayOf(centerX - spacing, centerY + spacing)
            )
            5 -> arrayOf(
                floatArrayOf(centerX - spacing, centerY - spacing),
                floatArrayOf(centerX + spacing, centerY + spacing),
                floatArrayOf(centerX + spacing, centerY - spacing),
                floatArrayOf(centerX - spacing, centerY + spacing),
                floatArrayOf(centerX, centerY)
            )
            else -> arrayOf(
                floatArrayOf(centerX - spacing, centerY - spacing),
                floatArrayOf(centerX + spacing, centerY + spacing),
                floatArrayOf(centerX + spacing, centerY - spacing),
                floatArrayOf(centerX - spacing, centerY + spacing),
                floatArrayOf(centerX - spacing, centerY),
                floatArrayOf(centerX + spacing, centerY)
            )
        }

        for (i in 0 until numDots) {
            val x = pointCoords[i][0]
            val y = pointCoords[i][1]
            canvas.drawCircle(x, y, dotRadius, dotPaint)
        }
    }

    fun setNumDots(dots: Int) {
        numDots = dots
        invalidate()
    }
}


