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

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.ui.core.component.RootSwipeToDismiss
import app.jjerrell.root.records.android.ui.theme.RootRecordsTheme
import app.jjerrell.root.records.service.model.Category

@Composable
internal fun CategoryListView(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onCategoryClick: (id: Int) -> Unit,
    onCategoryDelete: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = categories) { category: Category ->
            RootSwipeToDismiss(
                onSwipeValue = { swipeValue: SwipeToDismissBoxValue ->
                    if (swipeValue == SwipeToDismissBoxValue.EndToStart) {
                        onCategoryDelete(category.id!!)
                    }
                },
                content = {
                    CategoryListItem(
                        modifier = Modifier.fillMaxWidth(),
                        category = category,
                        onCategoryClick = { onCategoryClick(category.id!!) }
                    )
                }
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryListView_Preview() {
    RootRecordsTheme {
        CategoryListView(
            modifier = Modifier.fillMaxWidth(),
            categories =
                listOf(
                    Category(
                        name = "Work",
                        description = "For things that enable other things",
                        colorValue = Color.Black.toArgb()
                    ),
                    Category(
                        name = "Personal",
                        description = "The things you do because you work",
                        colorValue = Color.Black.toArgb()
                    ),
                    Category(
                        name = "Family",
                        description = "The people you do the work for",
                        colorValue = Color.Black.toArgb()
                    ),
                    Category(
                        name = "Friends",
                        description =
                            "People that also work so they can do things that you maybe do together",
                        colorValue = Color.Black.toArgb()
                    )
                ),
            onCategoryClick = {},
            onCategoryDelete = {}
        )
    }
}
