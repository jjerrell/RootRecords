package dev.jjerrell.root.records.android.ui.settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.jjerrell.root.records.android.RootRecordsScreen
import dev.jjerrell.root.records.android.ui.components.RootCard

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    controller: NavController
) {
    LazyColumn(modifier = modifier) {
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

