package dev.jjerrell.root.records.android.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.android.extension.toColor
import dev.jjerrell.root.records.android.ui.components.OutlinedCircle
import dev.jjerrell.root.records.android.ui.components.RootCard
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.model.Category

class CategoriesViewModel : ViewModel() {
    private lateinit var repository: RootRecordsRepository

    var state = mutableStateOf(State())
        private set

    fun loadCategories(context: Context) {
        repository = RootRecordsRepository(DriverFactory(context))
        state.value = state.value.copy(
            isLoading = false,
            categories = repository.getCategories()
        )
    }

    data class State(
        val isLoading: Boolean = true,
        val categories: List<Category> = emptyList()
    )
}

@Composable
fun CategoriesView(
    modifier: Modifier = Modifier,
    vm: CategoriesViewModel = viewModel(),
    onCategoryClick: (Category) -> Unit,
) {
    val currentContext = LocalContext.current
    LaunchedEffect(Unit) {
        vm.loadCategories(currentContext)
    }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(vm.state.value.categories) { index, it ->
            CategoryRow(
                modifier = Modifier.testTag("CATEGORY_ROW_$index"),
                categoryItem = it,
                onClick = {
                    onCategoryClick(it)
                }
            )
        }
    }
}

@Composable
private fun CategoryRow(
    modifier: Modifier = Modifier,
    categoryItem: Category,
    onClick: () -> Unit
) {
    RootCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            categoryItem.color?.toColor()?.let {
                OutlinedCircle(
                    innerColor = it
                )
            }
            Text(categoryItem.name)
        }
    }
}
