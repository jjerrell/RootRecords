package app.jjerrell.root.records.android.feature.category.list

import BaseViewModel
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.service.model.Category
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val DEFAULT_CATEGORIES_JSON = "default_categories.json"

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

    fun checkShouldLoadDefaults() {
        Log.d("CategoryListViewModel", "checkShouldLoadDefaults")
        if (state.categories.isEmpty()) {
            viewModelScope.launch {
                repository.checkShouldLoadDefaults().let {
                    Log.d("CategoryListViewModel", "checkShouldLoadDefaults: $it")
                    state = state.copy(showLoadDefaultsPrompt = it)
                }
            }
        }
    }

    fun insertDefaultCategories(context: Context) {
        Log.d("CategoryListViewModel", "insertDefaultCategories")
        state = state.copy(isLoading = true)
        val categories = context.assets.open(DEFAULT_CATEGORIES_JSON)
        val jsonString = categories.bufferedReader().use { it.readText() }
        viewModelScope.launch {
            Log.d("CategoryListViewModel", "insertDefaultCategories: $jsonString")
            async { repository.populateCategories(jsonString) }.await()
            state = state.copy(
                isLoading = false,
                showLoadDefaultsPrompt = false,
                categories = repository.getCategories() ?: emptyList()
            )
            Log.d("CategoryListViewModel", "insertDefaultCategories: ${state.categories}")
        }
    }

    fun dismissPrompt() {
        Log.d("CategoryListViewModel", "dismissPrompt")
        viewModelScope.launch {
            repository.setShouldNotLoadDefaults()
        }
        state = state.copy(showLoadDefaultsPrompt = false)
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
        val showLoadDefaultsPrompt: Boolean = false,
        val categories: List<Category> = emptyList()
    )
}