package dev.jjerrell.root.records.android.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.datetime.toJavaInstant
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskEditView(
    modifier: Modifier = Modifier,
    vm: TaskEditViewModel = viewModel(),
    taskId: String?,
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
    var shouldShowDatePicker by remember { mutableStateOf(false) }
    var shouldShowTimePicker by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        item {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = vm.state.task?.name.orEmpty(),
                onValueChange = vm::setTaskName,
                placeholder = {
                    Text("Task Name")
                }
            )
        }
        item {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = vm.state.task?.description.orEmpty(),
                onValueChange = vm::setTaskDescription,
                minLines = 3,
                maxLines = 5,
                placeholder = {
                    Text("Task Description")
                }
            )
        }
        item {
            Row(

            ) {
                TextButton(
                    onClick = {
                        shouldShowDatePicker = true
                    }
                ) {
                    Text(
                        text = vm.state.task?.timestamp?.toJavaInstant()?.let {
                            SimpleDateFormat.getDateInstance().format(
                                Date.from(it)
                            )
                        } ?: "Select Date"
                    )
                }
                TextButton(
                    onClick = {
                        shouldShowTimePicker = true
                    }
                ) {
                    Text(
                        text = vm.state.task?.timestamp?.toJavaInstant()?.let {
                            SimpleDateFormat.getTimeInstance().format(
                                Date.from(it)
                            )
                        } ?: "Select Time"
                    )
                }
            }

        }
        item {
            TextButton(
                onClick = {
                    vm.saveTask(context = currentContext)
                    onTaskSaved()
                },
                enabled = vm.state.isDirty
            ) {
                Text("Save")
            }
        }
    }
    if (shouldShowDatePicker) {
        vm.state.task?.timestamp?.let { currentDate ->
            TaskDatePickerDialog(
                taskDateMillis = currentDate.toEpochMilliseconds(),
                onDateChange = { selectedDate ->
                    vm.setTaskDate(dateSeconds = selectedDate / 1000)
                },
                onClose = {
                    shouldShowDatePicker = false
                }
            )
        }
    }
    if (shouldShowTimePicker) {
        vm.state.task?.dateTime?.let { currentDate ->
            TaskTimePickerDialog(
                taskHours = currentDate.hour,
                taskMinutes = currentDate.minute,
                onTimeChange = { selectedHour, selectedMinute ->
                    vm.setTaskTime(selectedHour, selectedMinute)
                },
                onClose = {
                    shouldShowTimePicker = false
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TaskDatePickerDialog(
    modifier: Modifier = Modifier,
    taskDateMillis: Long,
    onClose: () -> Unit,
    onDateChange: (millis: Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = taskDateMillis)
    Dialog(
        onDismissRequest = onClose
    ) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatePicker(
                state = datePickerState
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onClose
                ) {
                    Text("Cancel")
                }
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onDateChange(it) }
                        onClose()
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TaskTimePickerDialog(
    modifier: Modifier = Modifier,
    taskHours: Int,
    taskMinutes: Int,
    onClose: () -> Unit,
    onTimeChange: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = taskHours,
        initialMinute = taskMinutes,
        is24Hour = false
    )
    Dialog(
        onDismissRequest = onClose
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onClose
                ) {
                    Text("Cancel")
                }
                TextButton(
                    onClick = {
                        onTimeChange(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        onClose()
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}