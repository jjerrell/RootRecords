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
package app.jjerrell.root.records.android.ui.core.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RootCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) =
    RootCard(
        modifier = modifier,
        cardColors =
            backgroundColor?.let {
                CardDefaults.cardColors(
                    containerColor = it.copy(alpha = 0.25f),
                    contentColor = contentColorFor(backgroundColor = it)
                )
            }
                ?: CardDefaults.cardColors(),
        onClick = onClick,
        content = content
    )

@Composable
fun RootCard(
    modifier: Modifier = Modifier,
    cardColors: CardColors = CardDefaults.cardColors(),
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = modifier, colors = cardColors, onClick = onClick) {
        Column(modifier = Modifier.padding(8.dp), content = content)
    }
}
