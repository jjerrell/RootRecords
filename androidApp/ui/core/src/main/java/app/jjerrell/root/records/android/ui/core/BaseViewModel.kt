import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import app.jjerrell.root.records.db.DatabaseFactory
import app.jjerrell.root.records.service.RootRecordsRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("root_records")

abstract class BaseViewModel : ViewModel() {
    protected lateinit var repository: RootRecordsRepository

    protected open fun init(context: Context) {
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(
                factory = DatabaseFactory(context = context),
                preferences = context.dataStore
            )
        }
    }
}