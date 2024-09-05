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

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun TaskListScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskListViewModel,
    onTaskClick: (id: Int) -> Unit,
    onTaskDelete: (id: Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { viewModel.loadTasks(context) { viewModel.checkShouldLoadDefaults() } }
    TaskListView(
        modifier = modifier.fillMaxSize(),
        tasks = viewModel.state.tasks,
        onTaskClick = onTaskClick,
        onTaskDelete = { onTaskDelete(it) }
    )
    if (viewModel.state.showLoadDefaultsPrompt) {
        Log.d("TaskListScreen", "Showing prompt")
        AlertDialog(
            onDismissRequest = viewModel::dismissPrompt,
            dismissButton = {
                TextButton(onClick = viewModel::dismissPrompt) { Text(text = "Dismiss") }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.insertDefaultTasks(context) }) {
                    Text(text = "Confirm")
                }
            },
            text = { Text(text = "Load example gardening tasks?") }
        )
    }
}
