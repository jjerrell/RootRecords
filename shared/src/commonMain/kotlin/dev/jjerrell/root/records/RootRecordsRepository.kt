package dev.jjerrell.root.records

import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.createRoomDatabase
import dev.jjerrell.root.records.model.Category
import dev.jjerrell.root.records.model.Task
import dev.jjerrell.root.records.model.db.CategoryDbEntity
import dev.jjerrell.root.records.model.db.TaskDbEntity
import dev.jjerrell.root.records.model.db.TaskWithCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

class RootRecordsRepository(databaseDriverFactory: DriverFactory) {
    private val database = createRoomDatabase(databaseDriverFactory)

    suspend fun getAllTasks(): List<Task> {
        return database
            .taskDao()
            .getAllTasks()
            .map { it.map(TaskWithCategory::toTask) }
            .first()
    }

    suspend fun getTaskById(id: String): Task {
        return database
            .taskDao()
            .getTaskById(id)
            .map { it.toTask() }
            .first()
    }

    suspend fun insertTask(item: Task) {
        return database
            .taskDao()
            .insertTask(item.toTaskDbEntity())
    }

    suspend fun updateTask(item: Task) {
        return database
            .taskDao()
            .updateTask(item.toTaskDbEntity())
    }

    suspend fun getCategories(): List<Category> {
        return database
            .categoryDao()
            .getAllCategories()
            .map { it.map(CategoryDbEntity::toCategory) }
            .first()
    }

    suspend fun getCategoryById(id: String): Category {
        return database
            .categoryDao()
            .getCategoryById(id)
            .map { it.toCategory() }
            .first()
    }

    suspend fun insertCategory(item: Category) {
        return database
            .categoryDao()
            .insertCategory(item.toCategoryDbEntity())
    }

    suspend fun updateCategory(item: Category) {
        return database
            .categoryDao()
            .updateCategory(item.toCategoryDbEntity())
    }
}

//region Room
private fun TaskWithCategory.toTask(): Task = Task(
    id = this.task.id,
    name = this.task.name,
    description = this.task.description,
    timestamp = Instant.fromEpochSeconds(this.task.date),
    category =  this.category?.toCategory()
)

private fun Task.toTaskDbEntity(): TaskDbEntity = TaskDbEntity(
    id = id,
    name = name,
    description = description,
    date = timestamp.epochSeconds,
    categoryId = category?.id
)

private fun CategoryDbEntity.toCategory(): Category = Category(
    id = id,
    name = name,
    color = color
)

private fun Category.toCategoryDbEntity(): CategoryDbEntity = CategoryDbEntity(
    id = id,
    name = name,
    color = color
)
//endregion