package app.jjerrell.root.records.android.feature.category.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.ui.theme.ColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

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
    val colorController = rememberColorPickerController()
    val canSaveCategory: Boolean by rememberUpdatedState(
        newValue = viewModel.state.selectedCategory?.name?.isNotBlank() == true
    )
    val rememberedColor = remember(viewModel.currentColor.value) { viewModel.currentColor.value }
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
            ColorPicker(
                modifier = Modifier.fillMaxWidth(),
                controller = colorController,
                onColorChanged = {
                    viewModel.updateCategoryColor(it)
                }
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(20))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(20))
                        .background(rememberedColor)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
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
}