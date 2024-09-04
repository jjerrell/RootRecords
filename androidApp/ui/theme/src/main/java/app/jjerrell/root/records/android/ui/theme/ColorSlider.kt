/*
 * RootRecords
 * Copyright (C) 2024  Jacob Jerrell (@jjerrell)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package app.jjerrell.root.records.android.ui.theme

import android.graphics.Bitmap
import android.graphics.Paint
import androidx.compose.ui.graphics.*

fun createColorSliderBitmap(width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    val paint = Paint()

    // Create a linear gradient
    val shader =
        android.graphics.LinearGradient(
            0f,
            0f,
            width.toFloat(),
            0f,
            intArrayOf(
                Color.Red.toArgb(),
                Color.Yellow.toArgb(),
                Color.Green.toArgb(),
                Color.White.toArgb(),
                Color.Cyan.toArgb(),
                Color.Blue.toArgb(),
                Color.Magenta.toArgb()
            ),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )

    paint.shader = shader

    // Draw the gradient onto the canvas
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

    return bitmap
}
