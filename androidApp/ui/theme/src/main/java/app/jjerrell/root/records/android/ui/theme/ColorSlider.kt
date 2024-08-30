package app.jjerrell.root.records.android.ui.theme

import android.graphics.Bitmap
import android.graphics.Paint
import androidx.compose.ui.graphics.*

fun createColorSliderBitmap(width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    val paint = Paint()

    // Create a linear gradient
    val shader = android.graphics.LinearGradient(
        0f, 0f, width.toFloat(), 0f,
        intArrayOf(
            Color.Red.toArgb(), Color.Yellow.toArgb(), Color.Green.toArgb(), Color.White.toArgb(),
            Color.Cyan.toArgb(), Color.Blue.toArgb(), Color.Magenta.toArgb()
        ),
        null,
        android.graphics.Shader.TileMode.CLAMP
    )

    paint.shader = shader

    // Draw the gradient onto the canvas
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

    return bitmap
}
