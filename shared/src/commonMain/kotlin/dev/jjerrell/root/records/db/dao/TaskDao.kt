package dev.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.jjerrell.root.records.model.db.TaskDbEntity
import dev.jjerrell.root.records.model.db.TaskWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskDbEntity)

    @Update
    suspend fun updateTask(task: TaskDbEntity)

    @Transaction
    @Query("SELECT * FROM taskDbEntity")
    fun getAllTasks(): Flow<List<TaskWithCategory>>

    @Transaction
    @Query("SELECT * FROM taskDbEntity WHERE id = :id")
    fun getTaskById(id: String): Flow<TaskWithCategory>
}