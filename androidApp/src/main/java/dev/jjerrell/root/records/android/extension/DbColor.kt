package dev.jjerrell.root.records.android.extension

import androidx.compose.ui.graphics.Color

fun Long.toColor(): Color = Color(
    color = android.graphics.Color.parseColor(
        "#%06X".format(this and 0xFFFFFF)
    )
)
