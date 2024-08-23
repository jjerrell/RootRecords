package app.jjerrell.root.records.android.feature.category

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.jjerrell.root.records.android.feature.category.list.CategoryListScreen
import app.jjerrell.root.records.android.feature.category.list.CategoryViewModel
import app.jjerrell.root.records.android.feature.navigation.RootRecordsNavigation

fun NavGraphBuilder.categoryGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsNavigation.Categories.route,
        route = RootRecordsNavigation.Categories.name
    ) {
        composable(RootRecordsNavigation.Categories.route) {
            val viewModel: CategoryViewModel = viewModel()
            CategoryListScreen(
                viewModel = viewModel,
                onCategoryClick = { id: Int ->
                    viewModel.setLoading(isLoading = true)
                    navController.navigate(RootRecordsNavigation.EditCategory.fromCategoryId(id.toString()))
                },
                onCategoryDelete = { id: Int ->
                    viewModel.deleteCategory(id)
                }
            )
        }
        composable(RootRecordsNavigation.AddCategory.route) {
            val viewModel: CategoryViewModel = viewModel()
            CategoryEditScreen(
                viewModel = viewModel,
                onClose = navController::popBackStack
            )
        }
        composable(RootRecordsNavigation.EditCategory.route) {
            val categoryId = it.arguments?.getString("categoryId")
            val viewModel: CategoryViewModel = viewModel()
            CategoryEditScreen(
                viewModel = viewModel,
                categoryId = categoryId?.toIntOrNull(),
                onClose = navController::popBackStack
            )
        }
    }
}
