package dev.jjerrell.root.records.android.ui.tasks

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.db.CategoryEntity
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.TaskEntity
import java.util.UUID

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
                taskId = it.id.id,
                taskName = it.name,
                taskDescription = it.description ?: "",
                taskDate = it.date,
                taskCategoryId = it.category_id?.id
            )
        }
    }

    fun newTask() {
        _state.value = State(isLoading = false)
    }

    fun saveTask(context: Context) {
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(DriverFactory(context))
        }
        repository.insertTask(
            TaskEntity(
                id = TaskEntity.Id(state.taskId),
                name = state.taskName,
                description = state.taskDescription,
                date = state.taskDate,
                category_id = state.taskCategoryId?.let {
                    CategoryEntity.Id(it)
                }
            )
        )
    }

    //region Update Task/State values
    fun setTaskName(taskName: String) {
        _state.value = _state.value.copy(taskName = taskName)
    }

    fun setTaskDescription(taskDescription: String) {
        _state.value = _state.value.copy(taskDescription = taskDescription)
    }

    fun setTaskDate(taskDate: String) {
        _state.value = _state.value.copy(taskDate = taskDate)
    }

    fun setTaskCategoryId(taskCategoryId: String?) {
        _state.value = _state.value.copy(taskCategoryId = taskCategoryId)
    }
    //endregion

    data class State(
        val isLoading: Boolean = true,
        val taskId: String = UUID.randomUUID().toString(),
        val taskName: String = "",
        val taskDescription: String = "",
        val taskDate: String = "",
        val taskCategoryId: String? = null
    )
}

@Composable
fun TaskEditView(
    modifier: Modifier = Modifier,
    vm: TaskEditViewModel = viewModel(),
    taskId: String?,
    // TODO: Track pristine state and enable save button.
    //  navigate back or prompt to store unsaved changes
    onTaskSaved: () -> Unit
) {
    val currentContext = LocalContext.current
    LaunchedEffect(Unit) {
        taskId?.let {
            vm.loadTask(currentContext, it)
        } ?: run {
            vm.newTask()
        }
    }
    LazyColumn(
        modifier = modifier
    ) {
        item {
            TextField(
                value = vm.state.taskName,
                onValueChange = vm::setTaskName,
                placeholder = {
                    Text("Task Name")
                }
            )
        }
        item {
            TextField(
                value = vm.state.taskDescription,
                onValueChange = vm::setTaskDescription,
                minLines = 3,
                maxLines = 5,
                placeholder = {
                    Text("Task Description")
                }
            )
        }
    }
}
