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
package app.jjerrell.root.records.android.feature.task.edit

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.ui.core.RootDefaults
import app.jjerrell.root.records.service.model.Category

@Composable
fun TaskEditScreen(
    modifier: Modifier = Modifier,
    taskId: Int? = null,
    viewModel: TaskEditViewModel,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { viewModel.loadTask(context = context, id = taskId) }

    val canSaveTask: Boolean by
        rememberUpdatedState(newValue = viewModel.state.selectedTask?.title?.isNotBlank() == true)

    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            value = viewModel.state.selectedTask?.title.orEmpty(),
            onValueChange = viewModel::updateTaskTitle,
            placeholder = { Text("Name") }
        )
        TextField(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            value = viewModel.state.selectedTask?.description.orEmpty(),
            onValueChange = viewModel::updateTaskDescription,
            placeholder = { Text("Description") }
        )
        val checkboxInteraction = remember { MutableInteractionSource() }
        val isCompleted = viewModel.state.selectedTask?.isCompleted ?: false
        Row(
            modifier =
                Modifier.padding(horizontal = 16.dp)
                    .clickable(
                        interactionSource = checkboxInteraction,
                        indication = LocalIndication.current,
                        onClick = { viewModel.setTaskCompleted(!isCompleted) }
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = RootDefaults.defaultArrangement
        ) {
            Checkbox(
                checked = isCompleted,
                onCheckedChange = null,
                interactionSource = checkboxInteraction
            )
            Text(text = "Completed")
        }
        TextButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = { viewModel.beginSelectingCategory(context) },
            enabled = !viewModel.state.isSelectingCategory
        ) {
            Text(viewModel.state.selectedTask?.category?.name ?: "Select category")
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.saveTask()
                    onClose()
                },
                enabled = canSaveTask
            ) {
                Text("Save")
            }
        }
    }

    if (viewModel.state.isSelectingCategory) {
        CategorySelectionSheet(
            categories = viewModel.state.categories,
            selectedCategory = viewModel.state.selectedTask?.category,
            onClose = { viewModel.stopSelectingCategory() },
            onCategorySelected = viewModel::updateSelectedCategory
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategorySelectionSheet(
    modifier: Modifier = Modifier,
    categories: List<Category>?,
    selectedCategory: Category?,
    onClose: () -> Unit,
    onCategorySelected: (Category) -> Unit
) {
    ModalBottomSheet(modifier = modifier, onDismissRequest = onClose) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories
                ?.takeUnless { it.isEmpty() }
                ?.let {
                    items(it) {
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable { onCategorySelected(it) }
                        ) {
                            Checkbox(checked = it == selectedCategory, onCheckedChange = null)
                            Text(text = it.name)
                        }
                    }
                }
                ?: item { Text(text = "No categories found") }
        }
    }
}
