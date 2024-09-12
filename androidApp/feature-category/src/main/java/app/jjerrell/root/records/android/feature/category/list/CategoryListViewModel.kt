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
package app.jjerrell.root.records.android.feature.category.list

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.service.RootPreferencesRepository
import app.jjerrell.root.records.service.RootRecordsRepository
import app.jjerrell.root.records.service.model.Category
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val DEFAULT_CATEGORIES_JSON = "default_categories.json"

class CategoryListViewModel(
    private val repository: RootRecordsRepository,
    private val preferencesRepository: RootPreferencesRepository
) : ViewModel() {
    var state by mutableStateOf(State())
        private set

    fun loadCategories(onComplete: () -> Unit = {}) {
        state = state.copy(isLoading = true)
        viewModelScope
            .launch {
                val categories = repository.getCategories()
                state = state.copy(isLoading = false, categories = categories ?: emptyList())
            }
            .invokeOnCompletion { onComplete() }
    }

    fun checkShouldLoadDefaults() {
        Log.d("CategoryListViewModel", "checkShouldLoadDefaults")
        if (state.categories.isEmpty()) {
            viewModelScope.launch {
                preferencesRepository.checkShouldLoadDefaultCategories().let {
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
            state =
                state.copy(
                    isLoading = false,
                    showLoadDefaultsPrompt = false,
                    categories = repository.getCategories() ?: emptyList()
                )
            Log.d("CategoryListViewModel", "insertDefaultCategories: ${state.categories}")
            preferencesRepository.setShouldAskAboutDefaultCategories(false)
        }
    }

    fun dismissPrompt() {
        Log.d("CategoryListViewModel", "dismissPrompt")
        viewModelScope.launch { preferencesRepository.setShouldAskAboutDefaultCategories(false) }
        state = state.copy(showLoadDefaultsPrompt = false)
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            async { repository.deleteCategory(id) }.await()
            state =
                state.copy(
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
