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
package app.jjerrell.root.records.android.feature.settings.list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.root.records.BuildConfig

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun SettingsListView(modifier: Modifier = Modifier) {
    val viewModel: SettingsListViewModel = viewModel()
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (BuildConfig.DEBUG) {
            developerOptionsItems(context = context, viewModel = viewModel)
        } else {
            dangerZoneItems(context = context, isDeveloperOptions = false, viewModel = viewModel)
        }
    }
}

@ExperimentalFoundationApi
private fun LazyListScope.developerOptionsItems(
    context: Context,
    viewModel: SettingsListViewModel
) {
    stickyHeader {
        Text("Developer Options")
        HorizontalDivider()
    }
    item {
        SettingsListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.exportDatabaseToJSON(context)
                Toast.makeText(context, "Exporting...", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = "Export Category JSON",
            )
        }
    }

    dangerZoneItems(context = context, isDeveloperOptions = true, viewModel = viewModel)
}

@ExperimentalFoundationApi
private fun LazyListScope.dangerZoneItems(
    context: Context,
    isDeveloperOptions: Boolean,
    viewModel: SettingsListViewModel
) {
    if (!isDeveloperOptions) {
        stickyHeader {
            Text(text = "Danger Zone", color = MaterialTheme.colorScheme.error)
            HorizontalDivider(color = MaterialTheme.colorScheme.error)
        }
        item {
            Text(
                text =
                    "WARNING: The options in this section could be destructive and cannot be reversed!",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
    item {
        SettingsListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.clearCategories(context)
                Toast.makeText(context, "Clearing categories...", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = "Remove all categories",
            )
        }
    }

    item {
        SettingsListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.clearTasks(context)
                Toast.makeText(context, "Clearing tasks...", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = "Remove all tasks",
            )
        }
    }

    item {
        SettingsListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.resetPreferences(context)
                Toast.makeText(context, "Clearing user preferences...", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = "Reset preferences",
            )
        }
    }
}
