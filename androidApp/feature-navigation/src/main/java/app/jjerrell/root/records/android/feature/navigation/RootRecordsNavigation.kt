package app.jjerrell.root.records.android.feature.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavDestination

sealed class RootRecordsNavigation(
    val name: String,
    @StringRes val titleResourceId: Int,
    val route: String = name.replace(" ", "_"),
    val addRoute: RootRecordsNavigation? = null
) {
    data object Tasks : RootRecordsNavigation(
        name = "Tasks",
        titleResourceId = R.string.fnav_tasks_title,
        route = "view_tasks",
        addRoute = AddTask
    )
    data object AddTask : RootRecordsNavigation(
        name = "Add Task",
        titleResourceId = R.string.fnav_add_task_title
    )
    data object EditTask : RootRecordsNavigation(
        name = "Edit Task",
        titleResourceId = R.string.fnav_edit_task_title,
        route = "EditTask/{taskId}"
    ) {
        fun fromTaskId(taskId: String) = EditTask.route.replace("{taskId}", taskId)
    }
    data object Categories : RootRecordsNavigation(
        name = "Categories",
        titleResourceId = R.string.fnav_categories_title,
        route = "view_categories",
        addRoute = AddCategory
    )
    data object AddCategory : RootRecordsNavigation(
        name = "Add Category",
        titleResourceId = R.string.fnav_add_category_title
    )
    data object EditCategory : RootRecordsNavigation(
        name = "Edit Category",
        titleResourceId = R.string.fnav_edit_category_title,
        route = "EditCategory/{categoryId}"
    ) {
        fun fromCategoryId(categoryId: String) = EditCategory.route.replace("{categoryId}", categoryId)
    }
    data object Settings : RootRecordsNavigation(
        name = "Settings",
        titleResourceId = R.string.fnav_settings_title,
        route = "view_settings"
    )
    data object About : RootRecordsNavigation(
        name = "About",
        titleResourceId = R.string.fnav_about_title
    )
    data object Help : RootRecordsNavigation(
        name = "Help",
        titleResourceId = R.string.fnav_help_title
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