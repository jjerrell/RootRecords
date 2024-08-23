package app.jjerrell.root.records.android.feature.category

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
                onCategoryDelete = { id: Int -> }
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

@Composable
fun CategoryEditScreen(
    modifier: Modifier = Modifier,
    categoryId: Int? = null,
    viewModel: CategoryViewModel,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.selectCategory(context = context, id = categoryId)
    }
    val category = remember { mutableStateOf(viewModel.state.selectedCategory) }
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            OutlinedTextField(
                value = category.value?.name.orEmpty(),
                onValueChange = viewModel::updateCategoryName
            )
        }
        item {
            Button(
                onClick = {
                    viewModel.saveCategory()
                    onClose()
                }
            ) {
                Text("Save")
            }
        }
    }
}