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
import app.jjerrell.root.records.android.feature.navigation.RootRecordsNavigation

fun NavGraphBuilder.categoryGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsNavigation.Categories.route,
        route = RootRecordsNavigation.Categories.name
    ) {
        composable(RootRecordsNavigation.Categories.route) {
            val viewModel: CategoryListViewModel = viewModel()
            CategoryListScreen(
                viewModel = viewModel,
                onCategoryClick = { id: Int ->
                    navController.navigate(RootRecordsNavigation.EditCategory.fromCategoryId(id.toString()))
                },
                onCategoryDelete = { id: Int ->
                    viewModel.deleteCategory(id)
                }
            )
        }
        composable(RootRecordsNavigation.AddCategory.route) {
            val viewModel: CategoryEditViewModel = viewModel()
            CategoryEditScreen(
                viewModel = viewModel,
                onClose = navController::popBackStack
            )
        }
        composable(RootRecordsNavigation.EditCategory.route) {
            val categoryId = it.arguments?.getString("categoryId")
            val viewModel: CategoryEditViewModel = viewModel()
            CategoryEditScreen(
                viewModel = viewModel,
                categoryId = categoryId?.toIntOrNull(),
                onClose = navController::popBackStack
            )
        }
    }
}
