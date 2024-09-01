package app.jjerrell.root.records.android.ui.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

internal class ColorPickerGridViewModel : ViewModel() {
    val state = mutableStateListOf<Pair<String, Color>?>(
        "Black" to Color(0xFF000000),
        "White" to Color(0xFFFFFFFF),
        "Blue" to Color(0xFF0074D9),
        "Red" to Color(0xFFFF4136),
        "Green" to Color(0xFF2ECC40),
        "Yellow" to Color(0xFFFFDC00),
        "Orange" to Color(0xFFFF851B),
        "Purple" to Color(0xFFB10DC9),
        "Light Blue" to Color(0xFF7FDBFF),
        "Pink" to Color(0xFFFF69B4),
        "Gold" to Color(0xFFFFD700),
        "Gray" to Color(0xFFAAAAAA),
        "Olive" to Color(0xFF3D9970),
        "Maroon" to Color(0xFF85144B),
        "Teal" to Color(0xFF39CCCC),
        "Magenta" to Color(0xFFF012BE),
        null
    )

    var selectedIndex: Int? by mutableStateOf(null)
    var isSelectingColor by mutableStateOf(false)

    fun updateSelectedIndex(index: Int?) {
        selectedIndex = index
        updateIsSelectingColor(value = false)
    }

    fun updateIsSelectingColor(value: Boolean) {
        isSelectingColor = value
    }
}