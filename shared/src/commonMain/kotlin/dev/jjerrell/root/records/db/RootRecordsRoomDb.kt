package dev.jjerrell.root.records.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.jjerrell.root.records.db.dao.CategoryDao
import dev.jjerrell.root.records.db.dao.TaskDao
import dev.jjerrell.root.records.model.db.CategoryDbEntity
import dev.jjerrell.root.records.model.db.TaskDbEntity

@Database(
    entities = [TaskDbEntity::class, CategoryDbEntity::class],
    version = 1
)
abstract class RootRecordsRoomDb : RoomDatabase(), LocalRoomDb {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}

interface LocalRoomDb {
    fun clearAllTables() {}
}