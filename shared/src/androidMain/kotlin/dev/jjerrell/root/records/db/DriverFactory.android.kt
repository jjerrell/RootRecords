package dev.jjerrell.root.records.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RootRecordsDb.Schema, context, "root_records.db")
    }
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<RootRecordsRoomDb> {
        return Room.databaseBuilder<RootRecordsRoomDb>(
            context = context.applicationContext,
            name = "root_records.db",
            factory = { RootRecordsRoomDb::class.instantiateImpl() }
        )
    }
}