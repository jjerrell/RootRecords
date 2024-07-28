package dev.jjerrell.root.records

import dev.jjerrell.root.records.db.CategoryEntity
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.TaskEntity
import dev.jjerrell.root.records.db.createDatabase
import dev.jjerrell.root.records.db.createRoomDatabase
import dev.jjerrell.root.records.model.Category
import dev.jjerrell.root.records.model.Task
import dev.jjerrell.root.records.model.db.CategoryDbEntity
import dev.jjerrell.root.records.model.db.TaskDbEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

class RootRecordsRepository(databaseDriverFactory: DriverFactory) {
    private val database = createDatabase(databaseDriverFactory)
    private val categoryQueries = database.categoryEntityQueries
    private val roomDb = createRoomDatabase(databaseDriverFactory)

    suspend fun getAllTasks(): List<Task> {
        return roomDb
            .taskDao()
            .getAllTasks()
            .map { it.map(TaskDbEntity::toTask) }
            .first()
    }

    suspend fun getTaskById(id: String): Task {
        return roomDb
            .taskDao()
            .getTaskById(id)
            .map { it.toTask() }
            .first()
    }

    suspend fun insertTask(item: Task) {
        return roomDb
            .taskDao()
            .insertTask(item.toTaskDbEntity())
    }

    suspend fun updateTask(item: Task) {
        return roomDb
            .taskDao()
            .updateTask(item.toTaskDbEntity())
    }

    fun getCategories(): List<Category> {
        return categoryQueries
            .selectAll()
            .executeAsList()
            .map(CategoryEntity::toCategory)
    }

    fun getCategoryById(id: String): Category {
        return categoryQueries
            .selectById(CategoryEntity.Id(id))
            .executeAsOne()
            .toCategory()
    }

    fun insertCategory(item: Category) {
        return categoryQueries.insertFullCategoryObject(item.toCategoryEntity())
    }

    fun updateCategory(item: Category) {
        return categoryQueries.updateByValues(
            name = item.name,
            color = item.color,
            id = CategoryEntity.Id(item.id)
        )
    }
}

//region Deprecated SQLDelight
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
//endregion

//region Room
private fun TaskDbEntity.toTask(): Task = Task(
    id = id,
    name = name,
    description = description,
    timestamp = Instant.fromEpochSeconds(date),
    category = category?.toCategory()
)

private fun Task.toTaskDbEntity(): TaskDbEntity = TaskDbEntity(
    id = id,
    name = name,
    description = description,
    date = timestamp.epochSeconds,
    category = category?.toCategoryDbEntity()
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