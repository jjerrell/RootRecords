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
package app.jjerrell.root.records.android.ui.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import app.jjerrell.root.records.android.feature.navigation.R

sealed class RootRecordsNavigation(
    val name: String,
    @StringRes val titleResourceId: Int,
    val route: String = name.replace(" ", "_"),
    val addRoute: RootRecordsNavigation? = null
) {
    data object Tasks :
        RootRecordsNavigation(
            name = "Tasks",
            titleResourceId = R.string.fnav_tasks_title,
            route = "view_tasks",
            addRoute = AddTask
        )
    data object AddTask :
        RootRecordsNavigation(name = "Add Task", titleResourceId = R.string.fnav_add_task_title)
    data object EditTask :
        RootRecordsNavigation(
            name = "Edit Task",
            titleResourceId = R.string.fnav_edit_task_title,
            route = "EditTask/{taskId}"
        ) {
        fun fromTaskId(taskId: String) = EditTask.route.replace("{taskId}", taskId)
    }
    data object Categories :
        RootRecordsNavigation(
            name = "Categories",
            titleResourceId = R.string.fnav_categories_title,
            route = "view_categories",
            addRoute = AddCategory
        )
    data object AddCategory :
        RootRecordsNavigation(
            name = "Add Category",
            titleResourceId = R.string.fnav_add_category_title
        )
    data object EditCategory :
        RootRecordsNavigation(
            name = "Edit Category",
            titleResourceId = R.string.fnav_edit_category_title,
            route = "EditCategory/{categoryId}"
        ) {
        fun fromCategoryId(categoryId: String) =
            EditCategory.route.replace("{categoryId}", categoryId)
    }
    data object Settings :
        RootRecordsNavigation(
            name = "Settings",
            titleResourceId = R.string.fnav_settings_title,
            route = "view_settings"
        )
    data object About :
        RootRecordsNavigation(name = "About", titleResourceId = R.string.fnav_about_title)
    data object Help :
        RootRecordsNavigation(name = "Help", titleResourceId = R.string.fnav_help_title)

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
