package dev.jjerrell.root.records

import dev.jjerrell.root.records.db.CategoryEntity
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.TaskEntity
import dev.jjerrell.root.records.db.createDatabase

class RootRecordsRepository(databaseDriverFactory: DriverFactory) {
    private val database = createDatabase(databaseDriverFactory)
    private val categoryQueries = database.categoryEntityQueries
    private val taskQueries = database.taskEntityQueries

    fun getAllTasks(): List<TaskEntity> {
        return taskQueries.selectAll().executeAsList()
    }

    fun getTaskById(id: String): TaskEntity {
        return taskQueries.selectTaskById(
            TaskEntity.Id(id)
        ).executeAsOne()
    }

    fun insertTask(entity: TaskEntity) {
        return taskQueries.insertFullTaskObject(entity)
    }

    fun getCategories(): List<CategoryEntity> {
        return categoryQueries.selectAll().executeAsList()
    }
}