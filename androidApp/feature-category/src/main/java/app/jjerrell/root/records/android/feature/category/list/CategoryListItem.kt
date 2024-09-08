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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import app.jjerrell.root.records.android.ui.core.component.RootCard
import app.jjerrell.root.records.android.ui.core.view.color
import app.jjerrell.root.records.android.ui.theme.RootRecordsTheme
import app.jjerrell.root.records.service.model.Category

@Composable
internal fun CategoryListItem(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: () -> Unit
) {
    RootCard(modifier = modifier, backgroundColor = category.color, onClick = onCategoryClick) {
        Text(text = category.name)
        category.description
            ?.takeUnless { it.isBlank() }
            ?.let { Text(text = it, style = MaterialTheme.typography.bodySmall) }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryListItem_Preview() {
    RootRecordsTheme {
        CategoryListItem(
            modifier = Modifier.fillMaxWidth(),
            category = Category(name = "Work", description = "", colorValue = Color.White.toArgb()),
            onCategoryClick = {}
        )
    }
}
