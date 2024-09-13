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
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import app.jjerrell.root.records.db.dao.CategoryDao
import app.jjerrell.root.records.db.dao.TaskDao
import app.jjerrell.root.records.db.dao.TaskEventDao
import app.jjerrell.root.records.db.entity.CategoryEntity
import app.jjerrell.root.records.db.entity.EventEntity
import app.jjerrell.root.records.db.entity.TaskEntity

@Database(
    entities = [CategoryEntity::class, TaskEntity::class, EventEntity::class],
    version = 1
)
@ConstructedBy(DatabaseConstructor::class)
abstract class RootRecordsRoomDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao
    abstract fun taskEventDao(): TaskEventDao
}
