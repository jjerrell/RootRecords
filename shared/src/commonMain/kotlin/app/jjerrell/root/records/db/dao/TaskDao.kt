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
import androidx.room.Transaction
import androidx.room.Update
import app.jjerrell.root.records.db.entity.TaskEntity
import app.jjerrell.root.records.db.entity.TaskWithCategoryAndEvents
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertTask(task: TaskEntity)

    @Update suspend fun updateTask(task: TaskEntity)

    @Transaction
    @Query("SELECT * FROM taskEntity")
    fun getAllTasks(): Flow<List<TaskWithCategoryAndEvents>>

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE category_id = :id")
    fun getAllTasksByCategory(id: Int): Flow<List<TaskWithCategoryAndEvents>>

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskWithCategoryAndEvents>

    @Query("DELETE FROM taskEntity WHERE id = :id") suspend fun deleteTaskById(id: Int)
}
