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
import kotlinx.coroutines.launch

class CategoryListViewModel : ViewModel() {
    var state by mutableStateOf(State())
        private set

    private lateinit var repository: RootRecordsRepository

    fun loadCategories(context: Context) {
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
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val categories: List<Category> = emptyList()
    )
}