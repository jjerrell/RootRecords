package dev.jjerrell.root.records.android.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.jjerrell.root.records.android.RootRecordsScreen
import dev.jjerrell.root.records.android.ui.components.RootCard

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    controller: NavController
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            RootCard(
                onClick = {
                    controller.navigate(
                        RootRecordsScreen.Categories.route
                    )
                }
            ) {
                Text("Categories")
            }
        }
    }
}

