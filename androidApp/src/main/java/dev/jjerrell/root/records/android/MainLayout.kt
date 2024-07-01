package dev.jjerrell.root.records.android

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.jjerrell.root.records.android.ui.tasks.taskGraph

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainLayout(
    modifier: Modifier = Modifier,
    landingScreen: RootRecordsScreen = RootRecordsScreen.Tasks
) {
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination = RootRecordsScreen.fromNavDestination(navBackStackEntry?.destination)
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = currentDestination?.titleResourceId ?: R.string.app_name
                        )
                    )
                },
                navigationIcon = {
                    if (currentDestination != null && currentDestination != landingScreen) {
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    controller.navigate(RootRecordsScreen.AddTask.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_task_button)
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = controller,
            startDestination = landingScreen.name
        ) {
            taskGraph(controller)
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
        titleResourceId = R.string.tasks_title,
        route = "view_tasks"
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

    companion object {
        fun fromNavDestination(navDestination: NavDestination?): RootRecordsScreen? {
            return when (navDestination?.route) {
                Tasks.route -> Tasks
                AddTask.route -> AddTask
                EditTask.route -> EditTask
                Categories.route -> Categories
                AddCategory.route -> AddCategory
                EditCategory.route -> EditCategory
                Settings.route -> Settings
                About.route -> About
                Help.route -> Help
                else -> null
            }
        }
    }
}