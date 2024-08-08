package dev.jjerrell.root.records.android.ui.settings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CategoryEditViewModel : ViewModel() {
    private lateinit var repository: RootRecordsRepository

    var state by mutableStateOf(State())
        private set

    fun loadCategory(context: Context, categoryId: String) {
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(DriverFactory(context))
        }
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getCategoryById(categoryId).let {
                    state = state.copy(
                        isLoading = false,
                        category = it
                    )
                }
            }
        }
    }

    fun newCategory() {
        state = State(
            isLoading = false,
            isNewCategory = true,
            category = Category(
                id = UUID.randomUUID().toString(),
                name = "",
                color = null
            )
        )
    }

    fun setCategoryName(categoryName: String) {
        val updatedCategory = state.category?.copy(name = categoryName)
        state = state.copy(isDirty = true, category = updatedCategory)
    }

    fun setCategoryColor(color: Color) {
        val updatedCategory = state.category?.copy(color = color.toArgb().toLong())
        state = state.copy(isDirty = true, category = updatedCategory)
    }

    data class State(
        val isLoading: Boolean = true,
        val isDirty: Boolean = false,
        val isNewCategory: Boolean = false,
        val category: Category? = null
    )
}
