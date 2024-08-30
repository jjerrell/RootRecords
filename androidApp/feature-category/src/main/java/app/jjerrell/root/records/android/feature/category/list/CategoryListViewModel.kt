package app.jjerrell.root.records.android.feature.category.list

import BaseViewModel
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.service.model.Category
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoryListViewModel : BaseViewModel() {
    var state by mutableStateOf(State())
        private set

    fun loadCategories(context: Context, onComplete: () -> Unit = {}) {
        state = state.copy(isLoading = true)
        init(context)
        viewModelScope.launch {
            val categories = repository.getCategories()
            state = state.copy(
                isLoading = false,
                categories = categories ?: emptyList()
            )
        }.invokeOnCompletion { onComplete() }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            async { repository.deleteCategory(id) }.await()
            state = state.copy(
                isLoading = false,
                categories = state.categories.filterNot { it.id == id }
            )
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val categories: List<Category> = emptyList()
    )
}