package app.jjerrell.root.records.android.feature.category.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.db.DatabaseFactory
import app.jjerrell.root.records.service.RootRecordsRepository
import app.jjerrell.root.records.service.model.Category
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    var state by mutableStateOf(State())
        private set

    private lateinit var repository: RootRecordsRepository

    fun loadCategories(context: Context, onComplete: () -> Unit = {}) {
        state = state.copy(isLoading = true)
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(DatabaseFactory(context = context))
        }
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

    fun setLoading(isLoading: Boolean) {
        state = state.copy(isLoading = isLoading)
    }

    fun selectCategory(context: Context, id: Int?) {
        loadCategories(context) {
            state = state.copy(
                isLoading = false,
                selectedCategory = state.categories
                    .find { it.id == id } ?: Category(name = "", color = 0)
            )
        }
    }

    fun updateCategoryName(name: String) {
        state = state.copy(
            selectedCategory = state.selectedCategory?.copy(name = name)
        )
    }

    fun saveCategory() {
        setLoading(isLoading = true)
        state.selectedCategory?.let {
            viewModelScope.launch {
                async { repository.insertCategory(it) }.await()
                async { repository.getCategories() }.await().let {
                    state = state.copy(
                        isLoading = false,
                        categories = it ?: emptyList()
                    )
                }
            }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val categories: List<Category> = emptyList(),
        val selectedCategory: Category? = null
    )
}