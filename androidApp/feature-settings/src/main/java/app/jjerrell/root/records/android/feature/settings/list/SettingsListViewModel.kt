package app.jjerrell.root.records.android.feature.settings.list

import BaseViewModel
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class SettingsListViewModel : BaseViewModel() {
    fun exportDatabaseToJSON(context: Context) {
        init(context)
        viewModelScope.launch {
            repository.getCategories()?.let {
                val jsonString = Json.encodeToString(it)
                val file = File(context.getExternalFilesDir(null), "categories.json")
                file.writeText(jsonString)
                Log.d(
                    "SettingsListViewModel",
                    "Categories exported to JSON File:\n${file.absolutePath}"
                )
            }
        }
    }

    fun clearCategories(context: Context) {
        init(context)
        viewModelScope.launch {
            Log.d("SettingsListViewModel", "clearCategories")
            repository.getCategories()?.forEach { category ->
                category.id?.let { categoryId -> repository.deleteCategory(categoryId) }
            }
        }
    }

    fun resetPreferences(context: Context) {
        init(context)
        viewModelScope.launch {
            repository.clearPreferences()
        }
    }
}