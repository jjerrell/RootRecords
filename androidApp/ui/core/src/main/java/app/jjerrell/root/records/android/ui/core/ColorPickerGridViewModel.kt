package app.jjerrell.root.records.android.ui.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

internal class ColorPickerGridViewModel : ViewModel() {
    val state = mutableStateListOf<Pair<String, Color>?>(
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
        selectedIndex = state.indexOfFirst { it?.second == color }
    }

    fun updateIsSelectingColor(value: Boolean) {
        isSelectingColor = value
    }
}