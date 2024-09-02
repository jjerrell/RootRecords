package app.jjerrell.root.records.db

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

expect class DatabaseFactory {
    fun createBuilder(): RoomDatabase.Builder<RootRecordsRoomDatabase>
}

fun createDatabase(factory: DatabaseFactory): RootRecordsRoomDatabase {
    return factory
        .createBuilder()
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
