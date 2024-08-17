package app.jjerrell.root.records.db

import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object RootRecordsDatabaseConstructor : RoomDatabaseConstructor<RootRecordsRoomDatabase>

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
