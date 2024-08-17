package app.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import app.jjerrell.root.records.db.entity.TaskEntity
import app.jjerrell.root.records.db.entity.TaskWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertTask(task: TaskEntity)

    @Update suspend fun updateTask(task: TaskEntity)

    @Transaction @Query("SELECT * FROM taskEntity") fun getAllTasks(): Flow<List<TaskWithCategory>>

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE category_id = :id")
    fun getAllTasksByCategory(id: Int): Flow<List<TaskWithCategory>>

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskWithCategory>
}
