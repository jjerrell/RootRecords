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
package app.jjerrell.root.records.android.feature.category.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.ui.core.ColorPickerGrid
import app.jjerrell.root.records.android.ui.core.color

@Composable
fun CategoryEditScreen(
    modifier: Modifier = Modifier,
    categoryId: Int? = null,
    viewModel: CategoryEditViewModel,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { viewModel.loadCategory(context = context, id = categoryId) }
    val canSaveCategory: Boolean by
        rememberUpdatedState(
            newValue = viewModel.state.selectedCategory?.name?.isNotBlank() == true
        )
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            value = viewModel.state.selectedCategory?.name.orEmpty(),
            onValueChange = viewModel::updateCategoryName,
            placeholder = { Text("Name") }
        )
        TextField(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            value = viewModel.state.selectedCategory?.description.orEmpty(),
            onValueChange = viewModel::updateCategoryDescription,
            placeholder = { Text("Description") }
        )
        ColorPickerGrid(
            modifier = Modifier.padding(horizontal = 16.dp),
            initiallySelectedColor = viewModel.state.selectedCategory?.color,
            onColorChanged = { viewModel.updateCategoryColor(it) }
        )
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.saveCategory()
                    onClose()
                },
                enabled = canSaveCategory
            ) {
                Text("Save")
            }
        }
    }
}
