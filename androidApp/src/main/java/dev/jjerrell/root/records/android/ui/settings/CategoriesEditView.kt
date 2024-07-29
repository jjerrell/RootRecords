package dev.jjerrell.root.records.android.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import dev.jjerrell.root.records.android.extension.toColor
import dev.jjerrell.root.records.android.ui.components.OutlinedCircle

@Composable
fun CategoryEditView(
    modifier: Modifier = Modifier,
    vm: CategoryEditViewModel = viewModel(),
    categoryId: String?,
    onCategorySaved: () -> Unit
) {
    val currentContext = LocalContext.current
    var shouldShowColorPicker by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        categoryId?.let {
            vm.loadCategory(currentContext, it)
        } ?: run {
            vm.newCategory()
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        item {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = vm.state.category?.name.orEmpty(),
                onValueChange = vm::setCategoryName,
                placeholder = {
                    Text("Category Name")
                }
            )
        }
        item {
            TextButton(
                onClick = {
                    shouldShowColorPicker = true
                }
            ) {
                Row {
                    Text(
                        text = "Select Color"
                    )
                    OutlinedCircle(
                        innerColor = vm.state.category?.color?.toColor() ?: Color.Transparent
                    )
                }
            }
        }
    }
    if (shouldShowColorPicker) {
        CategoryColorPickerDialog(
            selectedColor = vm.state.category?.color?.toColor() ?: Color.Transparent,
            onColorSelected = {
                vm.setCategoryColor(it)
            },
            onClose = {
                shouldShowColorPicker = false
            }
        )
    }
}

@Composable
private fun CategoryColorPickerDialog(
    modifier: Modifier = Modifier,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    onClose: () -> Unit
) {
    val controller = rememberColorPickerController()
    var color by remember { mutableStateOf(selectedColor) }
    Dialog(onDismissRequest = onClose) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HsvColorPicker(
                modifier = modifier
                    .fillMaxWidth()
                    .height(450.dp),
//            .padding(10.dp),
                controller = controller,
                initialColor = selectedColor,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    color = colorEnvelope.color
                }
            )
            AlphaSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
            )
            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onClose
                ) {
                    Text("Cancel")
                }
                TextButton(
                    onClick = {
                        onColorSelected(color)
                        onClose()
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}
