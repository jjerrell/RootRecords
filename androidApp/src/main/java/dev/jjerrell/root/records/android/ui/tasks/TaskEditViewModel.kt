package dev.jjerrell.root.records.android.ui.tasks

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.util.*
import kotlin.math.min

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
        val updatedDate = Instant.fromEpochSeconds(dateSeconds)
            .plus(_state.value.task?.dateTime?.time?.hour ?: 0, DateTimeUnit.HOUR)
            .plus(_state.value.task?.dateTime?.time?.minute ?: 0, DateTimeUnit.MINUTE)
        val updatedTask = _state.value.task?.copy(timestamp = updatedDate)
        _state.value = _state.value.copy(isDirty = true, task = updatedTask)
    }

    fun setTaskTime(hour: Int, minute: Int) {
        val foo = _state.value.task?.timestamp?.toJavaInstant()?.let {
            Date.from(it)
        }
        foo?.hours = hour
        foo?.minutes = minute
        foo?.let {
            val updatedTask = _state.value.task?.copy(timestamp = it.toInstant().toKotlinInstant())
            _state.value = _state.value.copy(isDirty = true, task = updatedTask)
        }
//        _state.value.task?.timestamp?.let {
//            val updatedDate = it
//                .plus(hour, DateTimeUnit.HOUR)
//                .plus(minute, DateTimeUnit.MINUTE)
//            val updatedTask = _state.value.task?.copy(timestamp = updatedDate)
//            _state.value = _state.value.copy(isDirty = true, task = updatedTask)
//        }
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