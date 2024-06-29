package dev.jjerrell.root.records

import dev.jjerrell.root.records.db.CategoryEntity
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.TaskEntity
import dev.jjerrell.root.records.db.createDatabase

class RootRecordsRepository(databaseDriverFactory: DriverFactory) {
    private val database = createDatabase(databaseDriverFactory)
    private val categoryQueries = database.categoryEntityQueries
    private val taskQueries = database.taskEntityQueries

    fun getCategories(): List<CategoryEntity> {
        return categoryQueries.selectAll().executeAsList()
    }

    fun getAllTasks(): List<TaskEntity> {
        return taskQueries.selectAll().executeAsList()
    }

    fun insertTask(entity: TaskEntity) {
        return taskQueries.insertFullTaskObject(entity)
    }
}