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
package app.jjerrell.root.records.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import app.jjerrell.root.records.db.dao.CategoryDao
import app.jjerrell.root.records.db.dao.TaskDao
import app.jjerrell.root.records.db.entity.CategoryEntity
import app.jjerrell.root.records.db.entity.TaskEntity

@Database(
    entities = [CategoryEntity::class, TaskEntity::class],
    version = 7,
    autoMigrations =
        [
            AutoMigration(from = 1, to = 2),
            AutoMigration(from = 2, to = 3),
            AutoMigration(from = 3, to = 4),
            AutoMigration(from = 4, to = 5),
            AutoMigration(from = 5, to = 6),
            AutoMigration(from = 6, to = 7)
        ]
)
abstract class RootRecordsRoomDatabase : RoomDatabase(), LocalRoomDb {
    abstract fun categoryDao(): CategoryDao

    abstract fun taskDao(): TaskDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}

/**
 * Temporary fix for generated `RoomDatabase` implementations which are missing `clearAllTables()`
 */
interface LocalRoomDb {
    fun clearAllTables() {}
}
