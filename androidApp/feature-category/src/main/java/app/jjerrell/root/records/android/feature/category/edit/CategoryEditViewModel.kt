package app.jjerrell.root.records.android.feature.category.edit

import BaseViewModel
import android.content.Context
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.service.model.Category
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoryEditViewModel : BaseViewModel() {
    var state by mutableStateOf(State())
        private set

    val currentColor = derivedStateOf {
        state.selectedCategory?.color?.let {
            val colorValue = Color(it)
            Log.d("ColorDebug", "Converted color: $colorValue") // Log for verification
            colorValue
        } ?: Color.White // Provide a default if null
    }

    fun loadCategory(context: Context, id: Int?) {
        state = state.copy(isLoading = true)
        init(context)
        viewModelScope.launch {
            val category = id?.let {
                async { repository.getCategoryById(id) }.await()
            } ?: Category(name = "", color = 0)
            state = state.copy(
                isLoading = false,
                selectedCategory = category
            )
        }
    }

    fun updateCategoryName(name: String) {
        state = state.copy(
            selectedCategory = state.selectedCategory?.copy(name = name)
        )
    }

    fun updateCategoryColor(color: Color?) {
        state = state.copy(
            selectedCategory = state.selectedCategory?.copy(color = color?.toArgb())
        )
    }

    fun saveCategory() {
        state = state.copy(isLoading = true)
        state.selectedCategory?.let {
            viewModelScope.launch {
                async { repository.insertCategory(it) }.await()
            }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val selectedCategory: Category? = null
    )
}

