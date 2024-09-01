package app.jjerrell.root.records.android.feature.category.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.ui.core.ColorPickerGrid

@Composable
fun CategoryEditScreen(
    modifier: Modifier = Modifier,
    categoryId: Int? = null,
    viewModel: CategoryEditViewModel,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadCategory(context = context, id = categoryId)
    }
    val canSaveCategory: Boolean by rememberUpdatedState(
        newValue = viewModel.state.selectedCategory?.name?.isNotBlank() == true
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            value = viewModel.state.selectedCategory?.name.orEmpty(),
            onValueChange = viewModel::updateCategoryName,
            placeholder = {
                Text("Category Name")
            }
        )
        ColorPickerGrid(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            initiallySelectedColor = viewModel.state.selectedCategory?.color?.let { Color(it) },
            onColorChanged = {
                viewModel.updateCategoryColor(it)
            }
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.saveCategory()
                    onClose()
                },
                enabled = canSaveCategory
            ) {
                Text("Save")
            }
        }
    }
}