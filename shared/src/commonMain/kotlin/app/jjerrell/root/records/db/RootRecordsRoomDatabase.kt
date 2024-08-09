package app.jjerrell.root.records.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.jjerrell.root.records.db.dao.CategoryDao
import app.jjerrell.root.records.db.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1
)
abstract class RootRecordsRoomDatabase : RoomDatabase(), LocalRoomDb {
    abstract fun categoryDao(): CategoryDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}

/**
 * Temporary fix for generated `RoomDatabase` implementations which are missing `clearAllTables()`
 */
interface LocalRoomDb {
    fun clearAllTables() {}
}