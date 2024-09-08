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

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@Composable
fun RootDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String? = null,
    text: String?,
    properties: DialogProperties = DialogProperties()
) {
    if (isVisible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            dismissButton = { TextButton(onClick = onDismiss) { Text(text = "Dismiss") } },
            confirmButton = { TextButton(onClick = onConfirm) { Text(text = "Confirm") } },
            title = title?.let { { Text(text = it) } },
            text = text?.let { { Text(text = it) } },
            properties = properties
        )
    }
}
