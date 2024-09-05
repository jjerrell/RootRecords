/*
 * RootRecords
 * Copyright (C) 2024  Jacob Jerrell (@jjerrell)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package app.jjerrell.root.records.android.ui.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
    LaunchedEffect(initiallySelectedColor) {
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false,
            ) {
                itemsIndexed(viewModel.state) { index, titleColorPair ->
                    SelectableBox(
                        modifier = Modifier.size(50.dp),
                        titleColorPair = titleColorPair,
                        isSelected = viewModel.selectedIndex == index,
                        onSelected = {
                            viewModel.updateSelectedIndex(index)
                            onColorChanged(titleColorPair?.second)
                        }
                    ) {
                        if (titleColorPair == null) {
                            Text(text = "No Color", textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        } else {
            val selectedPair = viewModel.selectedIndex?.let { viewModel.state[it] }
            SelectableBox(
                modifier =
                    Modifier.let {
                        if (selectedPair == null) {
                            it.wrapContentWidth()
                        } else {
                            it.size(50.dp)
                        }
                    },
                titleColorPair = selectedPair,
                isSelected = true,
                onSelected = { viewModel.updateIsSelectingColor(true) }
            ) {
                if (selectedPair == null) {
                    Text(
                        modifier = Modifier.padding(8.dp),
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
    modifier: Modifier = Modifier,
    titleColorPair: Pair<String, Color>?,
    isSelected: Boolean,
    onSelected: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    val borderSize = if (isSelected) 2.dp else 0.dp
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(20))
                .border(borderSize, MaterialTheme.colorScheme.primary, RoundedCornerShape(20))
                .let { titleColorPair?.let { pair -> it.background(pair.second) } ?: it }
                .clickable { onSelected() },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
