package dev.jjerrell.root.records

import android.graphics.Color

actual class PlatformColor actual constructor(colorValue: Long) {
    val color = Color.parseColor("#%06X".format(colorValue and 0xFFFFFF))
}