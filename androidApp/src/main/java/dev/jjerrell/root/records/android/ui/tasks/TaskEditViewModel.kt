package dev.jjerrell.root.records.android.ui.tasks

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.util.*

class TaskEditViewModel : ViewModel() {
    private lateinit var repository: RootRecordsRepository

    private var _state = mutableStateOf(State())
    val state: State
        get() = _state.value

    fun loadTask(context: Context, taskId: String) {
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(DriverFactory(context))
        }
        repository.getTaskById(taskId).let {
            _state.value = _state.value.copy(
                isLoading = false,
                task = it
            )
        }
    }

    fun newTask() {
        _state.value = State(
            isLoading = false,
            isNewTask = true,
            task = Task(
                id = UUID.randomUUID().toString(),
                name = "",
                description = "",
                timestamp = Clock.System.now(),
                category = null
            )
        )
    }

    fun saveTask(context: Context) {
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(DriverFactory(context))
        }
        state.task?.let {
            if (state.isNewTask) {
                repository.insertTask(it)
            } else {
                repository.updateTask(it)
            }
        }
    }

    //region Update Task/State values
    fun setTaskName(taskName: String) {
        val updatedTask = _state.value.task?.copy(name = taskName)
        _state.value = _state.value.copy(isDirty = true, task = updatedTask)
    }

    fun setTaskDescription(taskDescription: String) {
        val updatedTask = _state.value.task?.copy(description = taskDescription)
        _state.value = _state.value.copy(isDirty = true, task = updatedTask)
    }

    fun setTaskDate(dateSeconds: Long) {
        val selectedLocalDateTime = Instant.fromEpochSeconds(dateSeconds)
            .toLocalDateTime(timeZone = TimeZone.UTC)

        _state.value.task?.let { task ->
            val originalLocalTime = task.dateTime.time.let {
                LocalTime(it.hour, it.minute)
            }

            val updatedDate = selectedLocalDateTime.date
                .atTime(originalLocalTime)
                .toInstant(TimeZone.currentSystemDefault())

            _state.value = _state.value.copy(
                isDirty = true,
                task = task.copy(timestamp = updatedDate)
            )
        }
    }

    fun setTaskTime(hour: Int, minute: Int) {
        val selectedTime = LocalTime(hour, minute)

        _state.value.task?.let { task ->
            val updatedDate = task.dateTime
                .date
                .atTime(selectedTime)
                .toInstant(TimeZone.currentSystemDefault())

            _state.value = _state.value.copy(
                isDirty = true,
                task = task.copy(timestamp = updatedDate)
            )
        }
    }

    fun setTaskCategoryId(taskCategoryId: String?) {
        val category = repository.getCategories().find { it.id == taskCategoryId }
        val updatedTask = _state.value.task?.copy(category = category)
        _state.value = _state.value.copy(isDirty = true, task = updatedTask)
    }
    //endregion

    data class State(
        val isLoading: Boolean = true,
        val isDirty: Boolean = false,
        val isNewTask: Boolean = false,
        val task: Task? = null
    )
}