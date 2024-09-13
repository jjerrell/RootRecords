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
package app.jjerrell.root.records.android.feature.task.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.service.RootPreferencesRepository
import app.jjerrell.root.records.service.RootRecordsRepository
import app.jjerrell.root.records.service.model.Task
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val DEFAULT_TASKS_JSON = "default_tasks.json"
private const val DEFAULT_CATEGORIES_JSON = "default_categories.json"

class TaskListViewModel(
    private val repository: RootRecordsRepository,
    private val preferencesRepository: RootPreferencesRepository
) : ViewModel() {
    var state by mutableStateOf(State())
        private set

    fun loadTasks(onComplete: () -> Unit = {}) {
        state = state.copy(isLoading = true)
        viewModelScope
            .launch {
                val tasks = repository.getTasks()
                state = state.copy(isLoading = false, tasks = tasks ?: emptyList())
            }
            .invokeOnCompletion { onComplete() }
    }

    fun checkShouldLoadDefaults() {
        if (state.tasks.isEmpty()) {
            viewModelScope.launch {
                preferencesRepository.checkShouldLoadDefaultTasks().let {
                    state = state.copy(showLoadDefaultsPrompt = it)
                }
            }
        }
    }

    fun insertDefaultTasks(context: Context) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val categoriesAreEmpty = async { repository.getCategories().isNullOrEmpty() }.await()
            if (categoriesAreEmpty) {
                async { repository.populateCategoriesFromFile(DEFAULT_CATEGORIES_JSON) }.await()
            }
            async { repository.populateTasksFromFile(DEFAULT_TASKS_JSON) }.await()
            state =
                state.copy(
                    isLoading = false,
                    showLoadDefaultsPrompt = false,
                    tasks = repository.getTasks() ?: emptyList()
                )
            preferencesRepository.setShouldAskAboutDefaultTasks(false)
        }
    }

    fun dismissPrompt() {
        viewModelScope.launch { preferencesRepository.setShouldAskAboutDefaultTasks(false) }
        state = state.copy(showLoadDefaultsPrompt = false)
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            async { repository.deleteTask(id) }.await()
            state = state.copy(isLoading = false, tasks = state.tasks.filterNot { it.id == id })
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val showLoadDefaultsPrompt: Boolean = false,
        val tasks: List<Task> = emptyList()
    )
}
