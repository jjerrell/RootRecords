package app.jjerrell.root.records.android.feature.category.list

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.root.records.service.model.Category

@Composable
internal fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel,
    onCategoryClick: (id: Int) -> Unit,
    onCategoryDelete: (id: Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadCategories(context)
    }
    CategoryListView(
        modifier = modifier.fillMaxSize(),
        categories = viewModel.state.categories,
        onCategoryClick = onCategoryClick,
        onCategoryDelete = { onCategoryDelete(it) }
    )
}
