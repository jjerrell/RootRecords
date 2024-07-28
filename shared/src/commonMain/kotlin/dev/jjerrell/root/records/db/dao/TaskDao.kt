package dev.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.jjerrell.root.records.model.db.TaskDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskDbEntity)

    @Update
    suspend fun updateTask(task: TaskDbEntity)

    @Query("SELECT * FROM taskDbEntity")
    fun getAllTasks(): Flow<List<TaskDbEntity>>

    @Query("SELECT * FROM taskDbEntity WHERE id = :id")
    fun getTaskById(id: String): Flow<TaskDbEntity>
}