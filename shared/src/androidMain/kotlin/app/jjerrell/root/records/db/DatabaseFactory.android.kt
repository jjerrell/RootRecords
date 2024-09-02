package app.jjerrell.root.records.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import app.jjerrell.root.records.service.model.Category
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

actual class DatabaseFactory(private val context: Context) {
    @OptIn(DelicateCoroutinesApi::class)
    actual fun createBuilder(): RoomDatabase.Builder<RootRecordsRoomDatabase> {
        val path = context.getDatabasePath("RootRecords.db").absolutePath
        return Room.databaseBuilder<RootRecordsRoomDatabase>(
                context = context.applicationContext,
                name = path,
                factory = { RootRecordsRoomDatabase::class.instantiateImpl() }
            )
            .enableMultiInstanceInvalidation()
//            .addCallback(object : RoomDatabase.Callback() {
//                override fun onCreate(connection: SQLiteConnection) {
//                    super.onCreate(connection)
//                    // Perform your data loading here
//                    GlobalScope.launch {
//                        val jsonString = context.assets.open("default_categories.json").bufferedReader().use { it.readText() }
////                        val gson = Gson()
//                        val categoryData: List<Category> = Json.decodeFromString(jsonString)
//
//                        val data: List<YourEntity> = gson.fromJson(jsonString, object : TypeToken<List<YourEntity>>() {}.type)
//                        getDatabase(context).yourDao().insertAll(data)
//                    }
//                }
//            })
    }
}
