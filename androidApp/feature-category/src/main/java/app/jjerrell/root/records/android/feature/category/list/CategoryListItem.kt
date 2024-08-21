package app.jjerrell.root.records.android.feature.category.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.jjerrell.root.records.android.ui.theme.RootRecordsTheme
import app.jjerrell.root.records.service.model.Category

@Composable
internal fun CategoryListItem(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: () -> Unit,
    onCategoryDelete: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onCategoryClick
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = category.name)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryListItem_Preview() {
    RootRecordsTheme {
        CategoryListItem(
            modifier = Modifier.fillMaxWidth(),
            category = Category(name = "Work", color = 0xFF000000),
            onCategoryClick = {},
            onCategoryDelete = {}
        )
    }
}