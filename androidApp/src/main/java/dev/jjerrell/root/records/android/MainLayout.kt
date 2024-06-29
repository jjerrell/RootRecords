package dev.jjerrell.root.records.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jjerrell.root.records.android.ui.TaskListView

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainLayout(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("RootRecords")
                }
            )
        }
    ) { paddingValues ->
        TaskListView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}