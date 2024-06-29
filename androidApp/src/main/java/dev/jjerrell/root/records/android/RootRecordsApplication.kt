package dev.jjerrell.root.records.android

import android.app.Application
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.RootRecordsDb

class RootRecordsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RootRecordsDb(
            driver = DriverFactory(
                this
            ).createDriver()
        )
    }

}