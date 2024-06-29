package dev.jjerrell.root.records.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.android.extension.toColor
import dev.jjerrell.root.records.db.DriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = RootRecordsRepository(DriverFactory(this))
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn {
                        items(items = repo.getCategories()) { category ->
                            Text(
                                text = category.name,
                                color = category.color?.toColor() ?: Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
}
