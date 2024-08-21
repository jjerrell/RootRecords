package app.jjerrell.root.records.android.feature.category.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.jjerrell.root.records.android.ui.theme.RootRecordsTheme
import app.jjerrell.root.records.service.model.Category

@Composable
internal fun CategoryListView(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onCategoryClick: (id: Int) -> Unit,
    onCategoryDelete: (id: Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = categories) { category: Category ->
            CategoryListItem(
                category = category,
                onCategoryClick = { onCategoryClick(category.id!!) },
                onCategoryDelete = { onCategoryDelete(category.id!!) }
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryListView_Preview() {
    RootRecordsTheme {
        CategoryListView(
            modifier = Modifier.fillMaxWidth(),
            categories = listOf(
                Category(name = "Work", color = 0xFF000000),
                Category(name = "Personal", color = 0xFF000000),
                Category(name = "Family", color = 0xFF000000),
                Category(name = "Friends", color = 0xFF000000)
            ),
            onCategoryClick = {},
            onCategoryDelete = {}
        )
    }
}