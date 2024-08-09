package app.jjerrell.root.records.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.jjerrell.root.records.db.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Query("SELECT * FROM categoryEntity")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categoryEntity WHERE category_id = :id")
    fun getCategoryById(id: Int): Flow<CategoryEntity>
}