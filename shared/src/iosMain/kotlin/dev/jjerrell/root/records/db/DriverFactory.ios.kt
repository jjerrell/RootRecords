package dev.jjerrell.root.records.db

import androidx.room.Room
import androidx.room.RoomDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import platform.Foundation.NSHomeDirectory

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RootRecordsDb.Schema, "root_records.db")
    }
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<RootRecordsRoomDb> {
        val dbFilePath = NSHomeDirectory() + "/my_room.db"
        return Room.databaseBuilder<RootRecordsRoomDb>(
            name = dbFilePath,
            factory =  { RootRecordsRoomDb::class.instantiateImpl() }
        )
    }
}
