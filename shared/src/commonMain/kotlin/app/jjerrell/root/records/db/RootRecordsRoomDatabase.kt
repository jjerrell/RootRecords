package app.jjerrell.root.records.db

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import app.jjerrell.root.records.db.dao.CategoryDao
import app.jjerrell.root.records.db.dao.TaskDao
import app.jjerrell.root.records.db.entity.CategoryEntity
import app.jjerrell.root.records.db.entity.TaskEntity

@Database(
    entities = [CategoryEntity::class, TaskEntity::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 1, to = 2), AutoMigration(from = 2, to = 3)])
@ConstructedBy(RootRecordsDatabaseConstructor::class)
abstract class RootRecordsRoomDatabase : RoomDatabase(), LocalRoomDb {
    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao

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
