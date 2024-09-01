package app.jjerrell.root.records.android.ui.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ColorPickerGrid(
    modifier: Modifier = Modifier,
    initiallySelectedColor: Color? = null,
    onColorChanged: (Color?) -> Unit
) {
    val viewModel: ColorPickerGridViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.updateRootSelectedColor(initiallySelectedColor)
    }

    AnimatedContent(
        targetState = viewModel.isSelectingColor,
        modifier = modifier,
        label = "color-picker-grid"
    ) { inSelectionMode: Boolean ->
        if (inSelectionMode) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Adaptive(minSize = 50.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                userScrollEnabled = false,
            ) {
                itemsIndexed(viewModel.state) { index, titleColorPair ->
                    SelectableBox(
                        titleColorPair = titleColorPair,
                        isSelected = viewModel.selectedIndex == index,
                        onSelected = {
                            viewModel.updateSelectedIndex(index)
                            onColorChanged(titleColorPair?.second)
                        }
                    ) {
                        if (titleColorPair == null) {
                            Text(
                                text = "No Color",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        } else {
            val selectedPair = viewModel.selectedIndex?.let { viewModel.state[it] }
            SelectableBox(
                titleColorPair = selectedPair,
                isSelected = true,
                onSelected = {
                    viewModel.updateIsSelectingColor(true)
                }
            ) {
                if (selectedPair == null) {
                    Text(
                        text = "Select Color",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableBox(
    titleColorPair: Pair<String, Color>?,
    isSelected: Boolean,
    onSelected: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    val borderSize = if (isSelected) 2.dp else 0.dp
    Box(
        modifier = Modifier
            .requiredSize(50.dp)
            .clip(RoundedCornerShape(20))
            .border(borderSize, MaterialTheme.colorScheme.primary, RoundedCornerShape(20))
            .let {
                titleColorPair?.let { pair ->
                    it.background(pair.second)
                } ?: it
            }
            .clickable {
                onSelected()
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
