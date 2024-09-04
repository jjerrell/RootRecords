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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.ImageColorPicker
import kotlin.math.roundToInt

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    controller: ColorPickerController,
    onColorChanged: (Color) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val pickerSize = remember { mutableStateOf(Size(1f, 1f)) }
        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .height(35.dp)
                    .clip(RoundedCornerShape(20))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(20))
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        pickerSize.value =
                            Size(placeable.width.toFloat(), placeable.height.toFloat())
                        layout(placeable.width, placeable.height) { placeable.placeRelative(0, 0) }
                    }
        ) {
            ImageColorPicker(
                modifier = Modifier.size(pickerSize.value.width.dp, pickerSize.value.height.dp),
                paletteImageBitmap =
                    createColorSliderBitmap(
                            height = pickerSize.value.height.roundToInt(),
                            width = pickerSize.value.width.roundToInt(),
                        )
                        .asImageBitmap(),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    onColorChanged(colorEnvelope.color)
                }
            )
        }

        AlphaSlider(modifier = Modifier.fillMaxWidth().height(35.dp), controller = controller)

        BrightnessSlider(modifier = Modifier.fillMaxWidth().height(35.dp), controller = controller)
    }
}
