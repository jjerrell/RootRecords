package dev.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.jjerrell.root.records.model.db.CategoryDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryDbEntity)

    @Update
    suspend fun updateCategory(category: CategoryDbEntity)

    @Query("SELECT * FROM categoryDbEntity")
    fun getAllCategories(): Flow<List<CategoryDbEntity>>

    @Query("SELECT * FROM categoryDbEntity WHERE category_id = :id")
    fun getCategoryById(id: String): Flow<CategoryDbEntity>
}
