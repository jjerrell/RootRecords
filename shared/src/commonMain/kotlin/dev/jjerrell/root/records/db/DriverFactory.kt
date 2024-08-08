package dev.jjerrell.root.records.db

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * Driver factory
 *
 * See: https://cashapp.github.io/sqldelight/2.0.2/multiplatform_sqlite
 *
 * @constructor Create empty Driver factory
 */
expect class DriverFactory {
    fun createDriver(): SqlDriver
    fun getDatabaseBuilder(): RoomDatabase.Builder<RootRecordsRoomDb>
}

fun createDatabase(driverFactory: DriverFactory): RootRecordsDb {
    val driver = driverFactory.createDriver()
    val database = RootRecordsDb(driver)

    return database
}

fun createRoomDatabase(driverFactory: DriverFactory): RootRecordsRoomDb {
    return driverFactory
        .getDatabaseBuilder()
//        .addMigrations(MIGRATIONS)
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}