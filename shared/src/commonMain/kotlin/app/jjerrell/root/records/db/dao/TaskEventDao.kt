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
package app.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.jjerrell.root.records.db.entity.EventEntity

@Dao
interface TaskEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update suspend fun updateEvent(event: EventEntity)

    @Update suspend fun updateEvents(events: List<EventEntity>)

    @Query("SELECT * FROM eventEntity WHERE task_id = :taskId")
    suspend fun getEventsByTaskId(taskId: Int): List<EventEntity>

    @Query("DELETE FROM eventEntity WHERE id = :id") suspend fun deleteEventById(id: Int)
}
