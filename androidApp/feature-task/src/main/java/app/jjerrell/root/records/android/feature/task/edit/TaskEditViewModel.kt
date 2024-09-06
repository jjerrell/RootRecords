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
package app.jjerrell.root.records.android.feature.task.edit

import BaseViewModel
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.service.model.Category
import app.jjerrell.root.records.service.model.Task
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskEditViewModel : BaseViewModel() {
    var state by mutableStateOf(State())
        private set

    fun loadTask(context: Context, id: Int?) {
        state = state.copy(isLoading = true)
        init(context)
        viewModelScope.launch {
            val task =
                id?.let { async { repository.getTaskById(id) }.await() }
                    ?: Task(title = "", description = "", isCompleted = false, category = null)
            state = state.copy(isLoading = false, selectedTask = task)
        }
    }

    fun updateTaskTitle(title: String) {
        state = state.copy(selectedTask = state.selectedTask?.copy(title = title))
    }

    fun updateTaskDescription(description: String) {
        state = state.copy(selectedTask = state.selectedTask?.copy(description = description))
    }

    fun beginSelectingCategory(context: Context) {
        init(context)
        if (state.categories.isNullOrEmpty()) {
            viewModelScope.launch {
                val categories = repository.getCategories()
                Log.d("TaskEditViewModel", "categories: $categories")
                state = state.copy(categories = categories, isSelectingCategory = true)
            }
        } else {
            state = state.copy(isSelectingCategory = true)
        }
    }

    fun stopSelectingCategory() {
        state = state.copy(isSelectingCategory = false)
    }

    fun updateSelectedCategory(category: Category?) {
        state =
            state.copy(
                isSelectingCategory = false,
                selectedTask = state.selectedTask?.copy(category = category)
            )
    }

    fun setTaskCompleted(completed: Boolean) {
        state = state.copy(selectedTask = state.selectedTask?.copy(isCompleted = completed))
    }

    fun saveTask() {
        state = state.copy(isLoading = true)
        state.selectedTask?.let {
            viewModelScope.launch { async { repository.insertTask(it) }.await() }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val isSelectingCategory: Boolean = false,
        val selectedTask: Task? = null,
        val categories: List<Category>? = null
    )
}
