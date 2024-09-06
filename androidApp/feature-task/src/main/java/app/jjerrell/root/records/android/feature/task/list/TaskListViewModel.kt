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

import BaseViewModel
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import app.jjerrell.root.records.service.model.Task
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val DEFAULT_TASKS_JSON = "default_tasks.json"

class TaskListViewModel : BaseViewModel() {
    var state by mutableStateOf(State())
        private set

    fun loadTasks(context: Context, onComplete: () -> Unit = {}) {
        state = state.copy(isLoading = true)
        init(context)
        viewModelScope
            .launch {
                val tasks = repository.getTasks()
                state = state.copy(isLoading = false, tasks = tasks ?: emptyList())
            }
            .invokeOnCompletion { onComplete() }
    }

    fun checkShouldLoadDefaults() {
        Log.d("TaskListViewModel", "checkShouldLoadDefaults")
        if (state.tasks.isEmpty()) {
            viewModelScope.launch {
                repository.checkShouldLoadDefaultTasks().let {
                    Log.d("TaskListViewModel", "checkShouldLoadDefaults: $it")
                    state = state.copy(showLoadDefaultsPrompt = it)
                }
            }
        }
    }

    fun insertDefaultTasks(context: Context) {
        Log.d("TaskListViewModel", "insertDefaultTasks")
        state = state.copy(isLoading = true)
        val tasks = context.assets.open(DEFAULT_TASKS_JSON)
        val jsonString = tasks.bufferedReader().use { it.readText() }
        viewModelScope.launch {
            Log.d("TaskListViewModel", "insertDefaultTasks: $jsonString")
            async { repository.populateTasks(jsonString) }.await()
            state =
                state.copy(
                    isLoading = false,
                    showLoadDefaultsPrompt = false,
                    tasks = repository.getTasks() ?: emptyList()
                )
            Log.d("TaskListViewModel", "insertDefaultTasks: ${state.tasks}")
            repository.setShouldAskAboutDefaultTasks(false)
        }
    }

    fun dismissPrompt() {
        Log.d("TaskListViewModel", "dismissPrompt")
        viewModelScope.launch { repository.setShouldAskAboutDefaultTasks(false) }
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
