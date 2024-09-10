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

import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

expect object DatabaseConstructor : RoomDatabaseConstructor<RootRecordsRoomDatabase> {
    override fun initialize(): RootRecordsRoomDatabase
}

expect class DatabaseFactory {
    fun createBuilder(fileName: String): RoomDatabase.Builder<RootRecordsRoomDatabase>
}
