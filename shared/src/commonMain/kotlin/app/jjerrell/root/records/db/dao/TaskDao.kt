package app.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import app.jjerrell.root.records.db.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Transaction
    @Query("SELECT * FROM taskEntity")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE id = :id")
    fun getTaskById(id: String): Flow<TaskEntity>
}