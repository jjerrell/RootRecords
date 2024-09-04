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
package app.jjerrell.root.records.android.feature.category.edit

import BaseViewModel
import android.content.Context
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

    fun loadCategory(context: Context, id: Int?) {
        state = state.copy(isLoading = true)
        init(context)
        viewModelScope.launch {
            val category =
                id?.let { async { repository.getCategoryById(id) }.await() }
                    ?: Category(name = "", description = "", colorValue = 0)
            state = state.copy(isLoading = false, selectedCategory = category)
        }
    }

    fun updateCategoryName(name: String) {
        state = state.copy(selectedCategory = state.selectedCategory?.copy(name = name))
    }

    fun updateCategoryDescription(description: String) {
        state =
            state.copy(selectedCategory = state.selectedCategory?.copy(description = description))
    }

    fun updateCategoryColor(color: Color?) {
        state =
            state.copy(
                selectedCategory = state.selectedCategory?.copy(colorValue = color?.toArgb())
            )
    }

    fun saveCategory() {
        state = state.copy(isLoading = true)
        state.selectedCategory?.let {
            viewModelScope.launch { async { repository.insertCategory(it) }.await() }
        }
    }

    data class State(val isLoading: Boolean = false, val selectedCategory: Category? = null)
}
