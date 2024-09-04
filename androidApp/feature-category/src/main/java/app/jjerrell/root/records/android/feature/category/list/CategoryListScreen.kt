package app.jjerrell.root.records.android.feature.category.list

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel,
    onCategoryClick: (id: Int) -> Unit,
    onCategoryDelete: (id: Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadCategories(context) {
            viewModel.checkShouldLoadDefaults()
        }
    }
    CategoryListView(
        modifier = modifier.fillMaxSize(),
        categories = viewModel.state.categories,
        onCategoryClick = onCategoryClick,
        onCategoryDelete = { onCategoryDelete(it) }
    )
    if (viewModel.state.showLoadDefaultsPrompt) {
        Log.d("CategoryListScreen", "Showing prompt")
        AlertDialog(
            onDismissRequest = viewModel::dismissPrompt,
            dismissButton = {
                TextButton(
                    onClick = viewModel::dismissPrompt
                ) {
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.insertDefaultCategories(context)
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            text = {
                Text(text = "Load default gardening categories?")
            }
        )
    }
}
