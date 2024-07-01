package dev.jjerrell.root.records.android.ui.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.jjerrell.root.records.android.RootRecordsScreen

fun NavGraphBuilder.taskGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsScreen.Tasks.route,
        route = RootRecordsScreen.Tasks.name
    ) {
        composable(RootRecordsScreen.Tasks.route) {
            TaskListView(
                modifier = Modifier
                    .fillMaxSize(),
                onTaskClick = {
                    navController.navigate(RootRecordsScreen.EditTask.fromTaskId(it.id.id))
                }
            )
        }

        composable(RootRecordsScreen.AddTask.route) {
            TaskEditView(
                modifier = Modifier
                    .fillMaxSize(),
                taskId = null,
                onTaskSaved = {
                    navController.navigate(RootRecordsScreen.Tasks.route)
                }
            )
        }

        composable(RootRecordsScreen.EditTask.route) {
            val taskId = it.arguments?.getString("taskId")
            TaskEditView(
                modifier = Modifier
                    .fillMaxSize(),
                taskId = taskId,
                onTaskSaved = {
                    navController.navigate(RootRecordsScreen.Tasks.route)
                }
            )
        }
    }
}