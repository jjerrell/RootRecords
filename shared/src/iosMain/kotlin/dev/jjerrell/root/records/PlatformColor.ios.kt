package dev.jjerrell.root.records

import platform.UIKit.UIColor

actual class PlatformColor actual constructor(colorValue: Long) {
    val color: UIColor = UIColor(
        red = ((colorValue and 0xFF0000) shr 16).toDouble() / 255.0,
        green = ((colorValue and 0x00FF00) shr 8).toDouble() / 255.0,
        blue = (colorValue and 0x0000FF).toDouble() / 255.0,
        alpha = 1.0
    )
}