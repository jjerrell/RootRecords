package app.jjerrell.root.records.android.feature.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.feature.category.list.CategoryViewModel

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
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.state.selectedCategory?.name.orEmpty(),
                onValueChange = viewModel::updateCategoryName,
                placeholder = {
                    Text("Category Name")
                }
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
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
}