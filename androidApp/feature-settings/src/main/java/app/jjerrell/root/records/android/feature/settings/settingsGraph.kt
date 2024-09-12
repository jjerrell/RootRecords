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
package app.jjerrell.root.records.android.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.jjerrell.root.records.android.feature.settings.list.SettingsListView
import app.jjerrell.root.records.android.feature.settings.list.SettingsListViewModel
import app.jjerrell.root.records.android.ui.navigation.RootRecordsNavigation
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.settingsGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsNavigation.Settings.route,
        route = RootRecordsNavigation.Settings.name
    ) {
        composable(RootRecordsNavigation.Settings.route) {
            val viewModel: SettingsListViewModel = koinViewModel()
            SettingsListView(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
        }
        composable(RootRecordsNavigation.Help.route) { Text("Help") }
        composable(RootRecordsNavigation.About.route) { Text("About") }
    }
}
