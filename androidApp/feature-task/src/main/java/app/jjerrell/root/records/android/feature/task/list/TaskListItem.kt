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

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.ui.core.color
import app.jjerrell.root.records.android.ui.theme.RootRecordsTheme
import app.jjerrell.root.records.service.model.Task

@Composable
internal fun TaskListItem(
    modifier: Modifier = Modifier,
    task: Task,
    onTaskClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors =
            task.category?.color?.let {
                CardDefaults.cardColors(
                    containerColor = it.copy(alpha = 0.25f),
                    contentColor = contentColorFor(it)
                )
            }
                ?: CardDefaults.cardColors(),
        onClick = onTaskClick
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = task.title)
            task.description
                .takeUnless { it.isBlank() }
                ?.let { Text(text = it, style = MaterialTheme.typography.bodySmall) }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskListItem_Preview() {
    RootRecordsTheme {
        TaskListItem(
            modifier = Modifier.fillMaxWidth(),
            task = Task(title = "Work", description = "", isCompleted = false, category = null),
            onTaskClick = {}
        )
    }
}
