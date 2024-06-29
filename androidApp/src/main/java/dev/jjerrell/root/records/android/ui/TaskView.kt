package dev.jjerrell.root.records.android.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.TaskEntity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class TaskViewModel : ViewModel() {
    private lateinit var repository: RootRecordsRepository

    var state = mutableStateOf(State())
        private set

    fun loadTasks(context: Context) {
        repository = RootRecordsRepository(DriverFactory(context))
        state.value = state.value.copy(
            isLoading = false,
            tasks = repository.getAllTasks()
        )
    }

    data class State(
        val isLoading: Boolean = true,
        val tasks: List<TaskEntity> = emptyList()
    )
}

@Composable
fun TaskListView(
    modifier: Modifier = Modifier,
    vm: TaskViewModel = viewModel(),
    onTaskClick: (TaskEntity) -> Unit
) {
    val currentContext = LocalContext.current
    LaunchedEffect(Unit) {
        vm.loadTasks(currentContext)
    }
    LazyColumn(modifier = modifier) {
        itemsIndexed(vm.state.value.tasks) { index, it ->
            TaskRow(
                modifier = Modifier.testTag("TASK_ROW_$index"),
                taskItem = it,
                onClick = {
                    onTaskClick(it)
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TaskRow(
    modifier: Modifier = Modifier,
    taskItem: TaskEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
        ) {
            Text(taskItem.name)
            taskItem.date.toLongOrNull()?.let {
                val date = SimpleDateFormat.getDateInstance(
                    SimpleDateFormat.MEDIUM
                ).format(Date.from(Instant.ofEpochSecond(it)))
                Text(date)
            }
        }
    }
}