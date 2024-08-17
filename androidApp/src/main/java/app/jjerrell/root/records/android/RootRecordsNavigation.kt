package app.jjerrell.root.records.android

import androidx.annotation.StringRes
import androidx.navigation.NavDestination

sealed class RootRecordsNavigation(
    val name: String,
    @StringRes val titleResourceId: Int,
    val route: String = name.replace(" ", "_"),
) {
    data object Tasks : RootRecordsNavigation(
        name = "Tasks",
        titleResourceId = R.string.tasks_title,
        route = "view_tasks"
    )
    data object AddTask : RootRecordsNavigation(
        name = "Add Task",
        titleResourceId = R.string.add_task_title
    )
    data object EditTask : RootRecordsNavigation(
        name = "Edit Task",
        titleResourceId = R.string.edit_task_title,
        route = "EditTask/{taskId}"
    ) {
        fun fromTaskId(taskId: String) = EditTask.route.replace("{taskId}", taskId)
    }
    data object Categories : RootRecordsNavigation(
        name = "Categories",
        titleResourceId = R.string.categories_title
    )
    data object AddCategory : RootRecordsNavigation(
        name = "Add Category",
        titleResourceId = R.string.add_category_title
    )
    data object EditCategory : RootRecordsNavigation(
        name = "Edit Category",
        titleResourceId = R.string.edit_category_title,
        route = "EditCategory/{categoryId}"
    ) {
        fun fromCategoryId(categoryId: String) = EditCategory.route.replace("{categoryId}", categoryId)
    }
    data object Settings : RootRecordsNavigation(
        name = "Settings",
        titleResourceId = R.string.settings_title,
        route = "view_settings"
    )
    data object About : RootRecordsNavigation(
        name = "About",
        titleResourceId = R.string.about_title
    )
    data object Help : RootRecordsNavigation(
        name = "Help",
        titleResourceId = R.string.help_title
    )

    companion object {
        fun fromNavDestination(navDestination: NavDestination?): RootRecordsNavigation? {
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