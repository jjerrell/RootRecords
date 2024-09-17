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
package app.jjerrell.root.records.android

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.jjerrell.root.records.android.ui.theme.DisplayType
import app.jjerrell.root.records.android.ui.theme.RootDisplays
import app.jjerrell.root.records.service.RootPreferencesRepository

class MainLayoutViewModel(private val preferencesRepository: RootPreferencesRepository) :
    ViewModel() {
    var displayTypes: RootDisplays by mutableStateOf(RootDisplays())
        private set

    suspend fun getTaskListDisplayType() {
        displayTypes =
            preferencesRepository.getTaskListDisplayType().let {
                RootDisplays(taskList = DisplayType.valueOf(it))
            }
    }
}
