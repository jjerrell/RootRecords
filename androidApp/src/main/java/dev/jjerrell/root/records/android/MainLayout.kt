package dev.jjerrell.root.records.android

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.jjerrell.root.records.android.ui.TaskListView

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainLayout(
    modifier: Modifier = Modifier,
    landingScreen: RootRecordsScreen = RootRecordsScreen.Tasks
) {
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination.let {
        when (it?.route) {
            RootRecordsScreen.Tasks.route -> RootRecordsScreen.Tasks
            RootRecordsScreen.AddTask.route -> RootRecordsScreen.AddTask
            RootRecordsScreen.EditTask.route -> RootRecordsScreen.EditTask
            RootRecordsScreen.Categories.route -> RootRecordsScreen.Categories
            RootRecordsScreen.AddCategory.route -> RootRecordsScreen.AddCategory
            RootRecordsScreen.EditCategory.route -> RootRecordsScreen.EditCategory
            RootRecordsScreen.Settings.route -> RootRecordsScreen.Settings
            RootRecordsScreen.About.route -> RootRecordsScreen.About
            RootRecordsScreen.Help.route -> RootRecordsScreen.Help
            else -> null
        }
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = currentDestination?.titleResourceId ?: R.string.app_name)
                    )
                },
                navigationIcon = {
                    if (currentDestination == RootRecordsScreen.EditTask) {
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = controller,
            startDestination = landingScreen.route
        ) {
            composable(RootRecordsScreen.Tasks.route) {
                TaskListView(
                    modifier = Modifier
                        .fillMaxSize(),
                    onTaskClick = {
                        controller.navigate(RootRecordsScreen.EditTask.fromTaskId(it.id))
                    }
                )
            }
            composable(RootRecordsScreen.AddTask.route) {

            }
            composable(RootRecordsScreen.EditTask.route) {
                Text("TODO: Edit Task Page")
            }
            composable(RootRecordsScreen.Categories.route) {

            }
            composable(RootRecordsScreen.AddCategory.route) {

            }
            composable(RootRecordsScreen.EditCategory.route) {

            }
            composable(RootRecordsScreen.Settings.route) {

            }
            composable(RootRecordsScreen.About.route) {
                Text("About")
            }
            composable(RootRecordsScreen.Help.route) {
                Text("Help")
            }
        }
    }
}

sealed class RootRecordsScreen(
    val name: String,
    @StringRes val titleResourceId: Int,
    val route: String = name.replace(" ", "_"),
) {

    data object Tasks : RootRecordsScreen(
        name = "Tasks",
        titleResourceId = R.string.tasks_title
    )
    data object AddTask : RootRecordsScreen(
        name = "Add Task",
        titleResourceId = R.string.add_task_title
    )
    data object EditTask : RootRecordsScreen(
        name = "Edit Task",
        titleResourceId = R.string.edit_task_title,
        route = "EditTask/{taskId}"
    ) {
        fun fromTaskId(taskId: String) = EditTask.route.replace("{taskId}", taskId)
    }
    data object Categories : RootRecordsScreen(
        name = "Categories",
        titleResourceId = R.string.categories_title
    )
    data object AddCategory : RootRecordsScreen(
        name = "Add Category",
        titleResourceId = R.string.add_category_title
    )
    data object EditCategory : RootRecordsScreen(
        name = "Edit Category",
        titleResourceId = R.string.edit_category_title,
        route = "EditCategory/{categoryId}"
    ) {
        fun fromCategoryId(categoryId: String) = EditCategory.route.replace("{categoryId}", categoryId)
    }
    data object Settings : RootRecordsScreen(
        name = "Settings",
        titleResourceId = R.string.settings_title
    )
    data object About : RootRecordsScreen(
        name = "About",
        titleResourceId = R.string.about_title
    )
    data object Help : RootRecordsScreen(
        name = "Help",
        titleResourceId = R.string.help_title
    )
}