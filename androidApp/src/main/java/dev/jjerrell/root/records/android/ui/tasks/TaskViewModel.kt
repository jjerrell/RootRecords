package dev.jjerrell.root.records.android.ui.tasks

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel : ViewModel() {
    private lateinit var repository: RootRecordsRepository

    var state = mutableStateOf(State())
        private set

    fun loadTasks(context: Context) {
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(DriverFactory(context))
        }
        state.value = state.value.copy(isLoading = true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.value = state.value.copy(
                    isLoading = false,
                    tasks = repository.getAllTasks()
                )
            }
        }
    }

    data class State(
        val isLoading: Boolean = true,
        val tasks: List<Task> = emptyList()
    )
}
