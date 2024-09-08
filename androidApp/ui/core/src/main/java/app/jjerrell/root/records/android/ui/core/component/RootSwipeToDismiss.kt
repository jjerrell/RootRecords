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

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun RootSwipeToDismiss(
    modifier: Modifier = Modifier,
    swipeState: SwipeToDismissBoxState = rememberSwipeToDismissBoxState(),
    enableDismissFromStartToEnd: Boolean = false,
    enableDismissFromEndToStart: Boolean = true,
    onSwipeValue: (SwipeToDismissBoxValue) -> Unit,
    backgroundContent: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.Settled -> {}
        else ->
            LaunchedEffect(swipeState) {
                onSwipeValue(swipeState.currentValue)
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
    }
    SwipeToDismissBox(
        state = swipeState,
        modifier = modifier,
        enableDismissFromStartToEnd = enableDismissFromStartToEnd,
        enableDismissFromEndToStart = enableDismissFromEndToStart,
        backgroundContent = backgroundContent,
        content = content
    )
}
