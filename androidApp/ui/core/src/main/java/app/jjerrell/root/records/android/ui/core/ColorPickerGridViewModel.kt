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
package app.jjerrell.root.records.android.ui.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import app.jjerrell.root.records.service.model.Category

internal class ColorPickerGridViewModel : ViewModel() {
    val state =
        mutableStateListOf<Pair<String, Color>?>(
            "Black" to Color(0xFF000000), // Shade Tolerant
            "White" to Color(0xFFFFFFFF), // Pollinator Attractors
            "Blue" to Color(0xFF0074D9), // Perennials
            "Red" to Color(0xFFFF4136), // Fruit Veggies (Tomatoes, Peppers)
            "Green" to Color(0xFF2ECC40), // Leafy Greens
            "Yellow" to Color(0xFFFFDC00), // Companion Plans
            "Orange" to Color(0xFFFF851B), // Root Vegetables
            "Purple" to Color(0xFFB10DC9), // Berries
            "Light Blue" to Color(0xFF7FDBFF), // Cover crops
            "Pink" to Color(0xFFFF69B4), // Flowers
            "Gold" to Color(0xFFFFD700), // Vines and Climbers
            "Gray" to Color(0xFFAAAAAA), // Brassicas (Cabbage, Broccoli)
            "Olive" to Color(0xFF3D9970), // Herbs
            "Maroon" to Color(0xFF85144B), // Bulbs and Tubers
            "Teal" to Color(0xFF39CCCC), // Legumes
            "Magenta" to Color(0xFFF012BE), // Medicinal and Aromatic
            "Dark Green" to Color(0xFF006400),
            "Turquoise" to Color(0xFF40E0D0),
            "Peach" to Color(0xFFFFE5B4),
            "Crimson" to Color(0xFFDC143C),
            null
        )

    var selectedIndex: Int? by mutableStateOf(null)
    var isSelectingColor by mutableStateOf(false)

    fun updateSelectedIndex(index: Int?) {
        selectedIndex = index
        updateIsSelectingColor(value = false)
    }

    fun updateRootSelectedColor(color: Color?) {
        selectedIndex = state.indexOfFirst { it?.second == color }.takeUnless { it == -1 }
    }

    fun updateIsSelectingColor(value: Boolean) {
        isSelectingColor = value
    }
}

val Category.color: Color?
    get() = colorValue?.let { Color(it) }
