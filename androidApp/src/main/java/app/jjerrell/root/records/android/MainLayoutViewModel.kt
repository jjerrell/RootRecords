package app.jjerrell.root.records.android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.jjerrell.root.records.android.ui.theme.DisplayType
import app.jjerrell.root.records.android.ui.theme.RootDisplays
import app.jjerrell.root.records.service.RootPreferencesRepository

class MainLayoutViewModel(private val preferencesRepository: RootPreferencesRepository) :
    ViewModel() {
    var displayTypes: RootDisplays by mutableStateOf(RootDisplays())
        private set

    suspend fun getTaskListDisplayType() {
        displayTypes =
            preferencesRepository.getTaskListDisplayType().let {
                RootDisplays(taskList = DisplayType.valueOf(it))
            }
    }
}