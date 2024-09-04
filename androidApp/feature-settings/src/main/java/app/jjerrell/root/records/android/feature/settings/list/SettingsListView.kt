package app.jjerrell.root.records.android.feature.settings.list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsListView(
    modifier: Modifier = Modifier
) {
    val viewModel: SettingsListViewModel = viewModel()
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        developerOptionsItems(
            context = context,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.developerOptionsItems(
    context: Context,
    viewModel: SettingsListViewModel
) {
    stickyHeader {
        Text("Developer Options")
    }
    item {
        SettingsListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.exportDatabaseToJSON(context)
                Toast.makeText(
                    context,
                    "Exporting...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            Text(
                text = "Export Category JSON",
            )
        }
    }

    item {
        SettingsListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.clearCategories(context)
                Toast.makeText(
                    context,
                    "Clearing categories...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            Text(
                text = "Remove all categories",
            )
        }
    }

    item {
        SettingsListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.resetPreferences(context)
                Toast.makeText(
                    context,
                    "Clearing user preferences...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            Text(
                text = "Reset preferences",
            )
        }
    }
}