package dev.jjerrell.root.records.db

import app.cash.sqldelight.db.SqlDriver

/**
 * Driver factory
 *
 * See: https://cashapp.github.io/sqldelight/2.0.2/multiplatform_sqlite
 *
 * @constructor Create empty Driver factory
 */
expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): RootRecordsDb {
    val driver = driverFactory.createDriver()
    val database = RootRecordsDb(driver)

    return database
}