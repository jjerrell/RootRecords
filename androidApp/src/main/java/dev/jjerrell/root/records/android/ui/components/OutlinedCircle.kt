package dev.jjerrell.root.records.android.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedCircle(
    modifier: Modifier = Modifier,
    innerColor: Color,
    outlineColor: Color = Color.Black,
    outlineWidth: Float = 1f
) {
    Canvas(
        modifier = modifier
            .size(16.dp)
    ) {
        val radius = size.minDimension / 2
        drawCircle(
            color = innerColor,
            radius = radius
        )
        drawCircle(
            color = outlineColor,
            radius = radius,
            style = Stroke(width = outlineWidth)
        )
    }
}