/*
 * RootRecords
 * Copyright (C) 2024  Jacob Jerrell (@jjerrell)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package app.jjerrell.root.records.android

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import app.jjerrell.root.records.android.feature.category.di.categoryModule
import app.jjerrell.root.records.android.feature.settings.settingsModule
import app.jjerrell.root.records.android.feature.task.di.taskModule
import app.jjerrell.root.records.db.DatabaseFactory
import app.jjerrell.root.records.di.sharedModule
import app.jjerrell.root.records.service.FileReaderService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("root_records")

fun appModule() = module {
    single { FileReaderService(context = get()) }
    single { DatabaseFactory(context = get()) }
    single { get<Context>().dataStore }
}

class RootRecordsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RootRecordsApplication)
            androidLogger()
            modules(appModule() + sharedModule + categoryModule + taskModule + settingsModule)
        }
    }
}
