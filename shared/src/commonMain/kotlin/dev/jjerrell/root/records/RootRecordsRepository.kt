package dev.jjerrell.root.records

import dev.jjerrell.root.records.db.CategoryEntity
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.TaskEntity
import dev.jjerrell.root.records.db.createDatabase
import dev.jjerrell.root.records.model.Category
import dev.jjerrell.root.records.model.Task
import kotlinx.datetime.Instant

class RootRecordsRepository(databaseDriverFactory: DriverFactory) {
    private val database = createDatabase(databaseDriverFactory)
    private val categoryQueries = database.categoryEntityQueries
    private val taskQueries = database.taskEntityQueries

    fun getAllTasks(): List<Task> {
        return taskQueries
            .selectAll()
            .executeAsList()
            .map { taskEntity ->
                taskEntity.toTask { categoryId ->
                    categoryId?.let {
                        categoryQueries.selectById(it)
                            .executeAsOne()
                    }
                }
            }
    }

    fun getTaskById(id: String): Task {
        return taskQueries.selectTaskById(TaskEntity.Id(id))
            .executeAsOne()
            .toTask { categoryId ->
                categoryId?.let {
                    categoryQueries.selectById(it)
                        .executeAsOne()
                }
            }
    }

    fun insertTask(item: Task) {
        return taskQueries.insertFullTaskObject(item.toTaskEntity())
    }

    fun updateTask(item: Task) {
        return taskQueries.updateByValues(
            name = item.name,
            description = item.description,
            date = item.timestamp.epochSeconds,
            id = TaskEntity.Id(item.id)
        )
    }

    fun getCategories(): List<Category> {
        return categoryQueries
            .selectAll()
            .executeAsList()
            .map(CategoryEntity::toCategory)
    }


}

private fun TaskEntity.toTask(
    categoryQuery: (CategoryEntity.Id?) -> CategoryEntity?
): Task = Task(
    id = id.id,
    name = name,
    description = description.orEmpty(),
    timestamp = Instant.fromEpochSeconds(date),
    category = category_id?.let {
        categoryQuery(it)
            ?.toCategory()
    }
)

private fun Task.toTaskEntity(): TaskEntity = TaskEntity(
    id = TaskEntity.Id(id),
    name = name,
    description = description,
    date = timestamp.epochSeconds,
    category_id = category?.id?.let { CategoryEntity.Id(it) }
)

private fun CategoryEntity.toCategory(): Category = Category(
    id = id.id,
    name = name,
    color = color
)

private fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = CategoryEntity.Id(id),
    name = name,
    color = color

)