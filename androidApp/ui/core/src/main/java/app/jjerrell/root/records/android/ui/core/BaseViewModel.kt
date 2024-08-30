import android.content.Context
import androidx.lifecycle.ViewModel
import app.jjerrell.root.records.db.DatabaseFactory
import app.jjerrell.root.records.service.RootRecordsRepository

abstract class BaseViewModel : ViewModel() {
    protected lateinit var repository: RootRecordsRepository

    protected open fun init(context: Context) {
        if (!::repository.isInitialized) {
            repository = RootRecordsRepository(DatabaseFactory(context = context))
        }
    }
}