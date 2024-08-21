package app.jjerrell.root.records.db

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual class DatabaseFactory {
    actual fun createBuilder(): RoomDatabase.Builder<RootRecordsRoomDatabase> {
        val dbFilePath = NSHomeDirectory() + "/RootRecords.db"
        return Room.databaseBuilder<RootRecordsRoomDatabase>(
            name = dbFilePath,
            factory = { RootRecordsRoomDatabase::class.instantiateImpl() }
        )
    }
}
