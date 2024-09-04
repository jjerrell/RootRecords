package app.jjerrell.root.records.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import app.jjerrell.root.records.db.DatabaseFactory
import app.jjerrell.root.records.db.createDatabase
import app.jjerrell.root.records.db.entity.CategoryEntity
import app.jjerrell.root.records.db.entity.TaskEntity
import app.jjerrell.root.records.db.entity.TaskWithCategory
import app.jjerrell.root.records.service.model.Category
import app.jjerrell.root.records.service.model.Task
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

private const val HAS_ASKED_FOR_DEFAULT_CATEGORIES = "has_asked_for_default_categories"

class RootRecordsRepository(
    factory: DatabaseFactory,
    private val preferences: DataStore<Preferences>
) {
    private val db = createDatabase(factory)

    // region Category
    suspend fun insertCategory(category: Category) {
        db.categoryDao().insertCategory(category.toEntity())
    }

    suspend fun updateCategory(category: Category) {
        db.categoryDao().updateCategory(category.toEntity())
    }

    suspend fun deleteCategory(id: Int) {
        db.categoryDao().deleteCategoryById(id)
    }

    suspend fun getCategories(): List<Category>? {
        return db.categoryDao().getAllCategories().firstOrNull()?.map { it.toModel() }
    }

    suspend fun getCategoryById(id: Int): Category? {
        return db.categoryDao().getCategoryById(id).firstOrNull()?.toModel()
    }

    suspend fun checkShouldLoadDefaults(): Boolean {
        val defaultCategoriesKey = booleanPreferencesKey(HAS_ASKED_FOR_DEFAULT_CATEGORIES)
        return preferences.data
            .map { preferences -> preferences[defaultCategoriesKey] ?: true }
            .first()
    }

    suspend fun setShouldNotLoadDefaults() {
        val defaultCategoriesKey = booleanPreferencesKey(HAS_ASKED_FOR_DEFAULT_CATEGORIES)
        preferences.edit { preferences -> preferences[defaultCategoriesKey] = false }
    }

    suspend fun populateCategories(jsonString: String) {
        val categories = Json.decodeFromString<List<Category>>(jsonString)
        categories.forEach { db.categoryDao().insertCategory(it.toEntity()) }
    }
    // endregion

    // region Task
    suspend fun insertTask(task: Task) {
        db.taskDao().insertTask(task.toEntity())
    }

    suspend fun updateTask(task: Task) {
        db.taskDao().updateTask(task.toEntity())
    }

    suspend fun getTasks(): List<Task>? {
        return db.taskDao().getAllTasks().firstOrNull()?.map { it.toModel() }
    }

    suspend fun getTaskById(id: Int): Task? {
        return db.taskDao().getTaskById(id).firstOrNull()?.toModel()
    }
    // endregion
}

private fun Category.toEntity() =
    CategoryEntity(id = id ?: 0, name = name, description = description, color = colorValue)

private fun CategoryEntity.toModel() =
    Category(id = id, name = name, description = description, colorValue = color)

private fun Task.toEntity() =
    TaskEntity(
        id = id ?: 0,
        title = title,
        description = description,
        isCompleted = isCompleted,
        categoryId = categoryId?.id
    )

private fun TaskWithCategory.toModel() =
    Task(
        id = task.id,
        title = task.title,
        description = task.description,
        isCompleted = task.isCompleted,
        categoryId = category?.toModel()
    )
