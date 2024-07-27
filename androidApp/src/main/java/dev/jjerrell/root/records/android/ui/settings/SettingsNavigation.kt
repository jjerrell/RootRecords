package dev.jjerrell.root.records.android.ui.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.jjerrell.root.records.android.RootRecordsScreen

fun NavGraphBuilder.settingsGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsScreen.Settings.route,
        route = RootRecordsScreen.Settings.name
    ) {
        composable(RootRecordsScreen.Settings.route) {
            SettingsView(
                openCategories = {
                    navController.navigate(
                        RootRecordsScreen.Categories.route
                    )
                }
            )
        }
        composable(RootRecordsScreen.Categories.route) {
            CategoriesView(
                onCategoryClick = {
                    navController.navigate(RootRecordsScreen.EditCategory.fromCategoryId(it.id))
                }
            )
        }
        composable(RootRecordsScreen.AddCategory.route) {
            CategoryEditView(
                categoryId = null,
                onCategorySaved = {
                    navController.navigate(RootRecordsScreen.Categories.route)
                }
            )
        }
        composable(RootRecordsScreen.EditCategory.route) {
            val categoryId = it.arguments?.getString("categoryId")
            CategoryEditView(
                categoryId = categoryId,
                onCategorySaved = {
                    navController.navigate(RootRecordsScreen.Categories.route)
                }
            )
        }
    }
}