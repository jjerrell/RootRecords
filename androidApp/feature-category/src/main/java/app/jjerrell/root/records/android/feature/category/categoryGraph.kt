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
package app.jjerrell.root.records.android.feature.category

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.jjerrell.root.records.android.feature.category.edit.CategoryEditScreen
import app.jjerrell.root.records.android.feature.category.edit.CategoryEditViewModel
import app.jjerrell.root.records.android.feature.category.list.CategoryListScreen
import app.jjerrell.root.records.android.feature.category.list.CategoryListViewModel
import app.jjerrell.root.records.android.ui.navigation.RootRecordsNavigation
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.categoryGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsNavigation.Categories.route,
        route = RootRecordsNavigation.Categories.name
    ) {
        composable(RootRecordsNavigation.Categories.route) {
            val viewModel: CategoryListViewModel = koinViewModel()
            CategoryListScreen(
                viewModel = viewModel,
                onCategoryClick = { id: Int ->
                    navController.navigate(
                        RootRecordsNavigation.EditCategory.fromCategoryId(id.toString())
                    )
                },
                onCategoryDelete = { id: Int -> viewModel.deleteCategory(id) }
            )
        }
        composable(RootRecordsNavigation.AddCategory.route) {
            val viewModel: CategoryEditViewModel = koinViewModel()
            CategoryEditScreen(viewModel = viewModel, onClose = navController::popBackStack)
        }
        composable(RootRecordsNavigation.EditCategory.route) {
            val categoryId = it.arguments?.getString("categoryId")
            val viewModel: CategoryEditViewModel = koinViewModel()
            CategoryEditScreen(
                viewModel = viewModel,
                categoryId = categoryId?.toIntOrNull(),
                onClose = navController::popBackStack
            )
        }
    }
}
