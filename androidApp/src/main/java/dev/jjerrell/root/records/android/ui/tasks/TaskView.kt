package dev.jjerrell.root.records.android.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jjerrell.root.records.android.ui.components.RootCard
import dev.jjerrell.root.records.model.Task
import kotlinx.datetime.toJavaInstant
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskListView(
    modifier: Modifier = Modifier,
    vm: TaskViewModel = viewModel(),
    onTaskClick: (Task) -> Unit
) {
    val currentContext = LocalContext.current
    LaunchedEffect(Unit) {
        vm.loadTasks(currentContext)
    }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
private fun TaskRow(
    modifier: Modifier = Modifier,
    taskItem: Task,
    onClick: () -> Unit
) {
    RootCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(taskItem.name)
        val date = SimpleDateFormat.getDateInstance(
            SimpleDateFormat.MEDIUM
        ).format(
            Date.from(taskItem.timestamp.toJavaInstant())
        )
        Text(date)
    }
}
