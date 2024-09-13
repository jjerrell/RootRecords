package app.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.jjerrell.root.records.db.entity.EventEntity

@Dao
interface TaskEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Update
    suspend fun updateEvents(events: List<EventEntity>)

    @Query("SELECT * FROM eventEntity WHERE task_id = :taskId")
    suspend fun getEventsByTaskId(taskId: Int): List<EventEntity>

    @Query("DELETE FROM eventEntity WHERE id = :id")
    suspend fun deleteEventById(id: Int)


}