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
package app.jjerrell.root.records.android.feature.category.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import app.jjerrell.root.records.android.ui.core.component.RootDialog

@Composable
internal fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel,
    onCategoryClick: (id: Int) -> Unit,
    onCategoryDelete: (id: Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadCategories(context) { viewModel.checkShouldLoadDefaults() }
    }
    CategoryListView(
        modifier = modifier.fillMaxSize(),
        categories = viewModel.state.categories,
        onCategoryClick = onCategoryClick,
        onCategoryDelete = { onCategoryDelete(it) }
    )
    RootDialog(
        isVisible = viewModel.state.showLoadDefaultsPrompt,
        onDismiss = viewModel::dismissPrompt,
        onConfirm = { viewModel.insertDefaultCategories(context) },
        text = "Load example gardening categories?"
    )
}
