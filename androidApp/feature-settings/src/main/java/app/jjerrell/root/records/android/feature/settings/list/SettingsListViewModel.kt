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

import BaseViewModel
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import java.io.File
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsListViewModel : BaseViewModel() {
    fun exportDatabaseToJSON(context: Context) {
        init(context)
        viewModelScope.launch {
            repository.getCategories()?.let {
                val jsonString = Json.encodeToString(it)
                val file = File(context.getExternalFilesDir(null), "categories.json")
                file.writeText(jsonString)
                Log.d(
                    "SettingsListViewModel",
                    "Categories exported to JSON File:\n${file.absolutePath}"
                )
            }
        }
    }

    fun clearCategories(context: Context) {
        init(context)
        viewModelScope.launch {
            Log.d("SettingsListViewModel", "clearCategories")
            repository.getCategories()?.forEach { category ->
                category.id?.let { categoryId -> repository.deleteCategory(categoryId) }
            }
        }
    }

    fun clearTasks(context: Context) {
        init(context)
        viewModelScope.launch {
            Log.d("SettingsListViewModel", "clearTasks")
            repository.getTasks()?.forEach { task ->
                task.id?.let { taskId -> repository.deleteTask(taskId) }
            }
        }
    }

    fun resetPreferences(context: Context) {
        init(context)
        viewModelScope.launch { repository.clearPreferences() }
    }
}
