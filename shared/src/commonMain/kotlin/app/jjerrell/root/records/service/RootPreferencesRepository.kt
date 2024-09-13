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
package app.jjerrell.root.records.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val HAS_ASKED_FOR_DEFAULT_CATEGORIES = "has_asked_for_default_categories"
private const val HAS_ASKED_FOR_DEFAULT_TASKS = "has_asked_for_default_tasks"

enum class TaskListDisplayType {
    SEPARATE,
    GROUPED;

    companion object {
        const val KEY = "task_list_display_type"
    }
}

class RootPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    // region Category
    suspend fun checkShouldLoadDefaultCategories(): Boolean {
        val defaultCategoriesKey = booleanPreferencesKey(HAS_ASKED_FOR_DEFAULT_CATEGORIES)
        return dataStore.data
            .map { preferences -> preferences[defaultCategoriesKey] ?: true }
            .first()
    }

    suspend fun setShouldAskAboutDefaultCategories(value: Boolean) {
        val defaultCategoriesKey = booleanPreferencesKey(HAS_ASKED_FOR_DEFAULT_CATEGORIES)
        dataStore.edit { preferences -> preferences[defaultCategoriesKey] = value }
    }
    // endregion

    // region Task
    suspend fun checkShouldLoadDefaultTasks(): Boolean {
        val defaultTasksKey = booleanPreferencesKey(HAS_ASKED_FOR_DEFAULT_TASKS)
        return dataStore.data.map { preferences -> preferences[defaultTasksKey] ?: true }.first()
    }

    suspend fun setShouldAskAboutDefaultTasks(value: Boolean) {
        val defaultTasksKey = booleanPreferencesKey(HAS_ASKED_FOR_DEFAULT_TASKS)
        dataStore.edit { preferences -> preferences[defaultTasksKey] = value }
    }
    // endregion

    //region Display
    suspend fun getTaskListDisplayType(): TaskListDisplayType {
        val dataStoreKey = stringPreferencesKey(TaskListDisplayType.KEY)
        return dataStore.data.map { preferences ->
            preferences[dataStoreKey]?.let {
                TaskListDisplayType.valueOf(it)
            } ?: TaskListDisplayType.SEPARATE
        }.first()
    }

    suspend fun setTaskListDisplayType(value: TaskListDisplayType) {
        val dataStoreKey = stringPreferencesKey(TaskListDisplayType.KEY)
        dataStore.edit { preferences -> preferences[dataStoreKey] = value.name }
    }
    //endregion

    // region Maintenance
    suspend fun clearPreferences() {
        dataStore.edit { preferences -> preferences.clear() }
    }
    // endregion
}
