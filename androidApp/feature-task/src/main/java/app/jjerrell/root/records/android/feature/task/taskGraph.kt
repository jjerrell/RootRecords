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
package app.jjerrell.root.records.android.feature.task

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.jjerrell.root.records.android.feature.task.edit.TaskEditScreen
import app.jjerrell.root.records.android.feature.task.edit.TaskEditViewModel
import app.jjerrell.root.records.android.feature.task.list.TaskListScreen
import app.jjerrell.root.records.android.feature.task.list.TaskListViewModel
import app.jjerrell.root.records.android.ui.navigation.RootRecordsNavigation
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.taskGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsNavigation.Tasks.route,
        route = RootRecordsNavigation.Tasks.name
    ) {
        composable(RootRecordsNavigation.Tasks.route) {
            val viewModel: TaskListViewModel = koinViewModel()
            TaskListScreen(
                viewModel = viewModel,
                onTaskClick = { id: Int ->
                    navController.navigate(RootRecordsNavigation.EditTask.fromTaskId(id.toString()))
                },
                onTaskDelete = { id: Int -> viewModel.deleteTask(id) }
            )
        }
        composable(RootRecordsNavigation.AddTask.route) {
            val viewModel: TaskEditViewModel = koinViewModel()
            TaskEditScreen(viewModel = viewModel, onClose = navController::popBackStack)
        }
        composable(RootRecordsNavigation.EditTask.route) {
            val taskId = it.arguments?.getString("taskId")
            val viewModel: TaskEditViewModel = koinViewModel()
            TaskEditScreen(
                viewModel = viewModel,
                taskId = taskId?.toIntOrNull(),
                onClose = navController::popBackStack
            )
        }
    }
}
