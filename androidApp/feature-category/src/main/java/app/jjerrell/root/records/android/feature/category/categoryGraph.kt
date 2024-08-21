package app.jjerrell.root.records.android.feature.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.jjerrell.root.records.android.feature.category.list.CategoryListScreen
import app.jjerrell.root.records.android.feature.navigation.RootRecordsNavigation

fun NavGraphBuilder.categoryGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsNavigation.Categories.route,
        route = RootRecordsNavigation.Categories.name
    ) {
        composable(RootRecordsNavigation.Categories.route) {
            CategoryListScreen(
                onCategoryClick = { id: Int ->
                    navController.navigate(RootRecordsNavigation.EditCategory.fromCategoryId(id.toString()))
                },
                onCategoryDelete = { id: Int ->

                }
            )
        }
    }
}