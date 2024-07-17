package dev.jjerrell.root.records.android.ui.settings

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jjerrell.root.records.RootRecordsRepository
import dev.jjerrell.root.records.android.ui.components.RootCard
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.model.Category
import dev.jjerrell.root.records.model.Task
import kotlinx.datetime.toJavaInstant
import java.text.SimpleDateFormat
import java.util.*

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
    vm: CategoriesViewModel = viewModel()
) {
    val currentContext = LocalContext.current
    LaunchedEffect(Unit) {
        vm.loadCategories(currentContext)
    }
    LazyColumn(modifier = modifier) {
        itemsIndexed(vm.state.value.categories) { index, it ->
            CategoryRow(
                modifier = Modifier.testTag("CATEGORY_ROW_$index"),
                categoryItem = it,
                onClick = {

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
        Text(categoryItem.name)
//        val date = SimpleDateFormat.getDateInstance(
//            SimpleDateFormat.MEDIUM
//        ).format(
//            Date.from(taskItem.timestamp.toJavaInstant())
//        )
//        Text(date)
    }
}