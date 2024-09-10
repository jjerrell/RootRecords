/*
 * RootRecords
 * Copyright (C) 2024  Jacob Jerrell (@jjerrell)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package app.jjerrell.root.records.service

import app.jjerrell.root.records.db.DatabaseFactory
import app.jjerrell.root.records.db.entity.CategoryEntity
import app.jjerrell.root.records.db.entity.TaskEntity
import app.jjerrell.root.records.db.entity.TaskWithCategory
import app.jjerrell.root.records.service.model.Category
import app.jjerrell.root.records.service.model.Task
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class RootRecordsRepository(factory: DatabaseFactory) {
    private val db = factory.createBuilder("RootRecords.db").build()

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

    suspend fun deleteTask(id: Int) {
        db.taskDao().deleteTaskById(id)
    }

    suspend fun getTasks(): List<Task>? {
        return db.taskDao().getAllTasks().firstOrNull()?.map { it.toModel() }
    }

    suspend fun getTaskById(id: Int): Task? {
        return db.taskDao().getTaskById(id).firstOrNull()?.toModel()
    }

    suspend fun populateTasks(jsonString: String) {
        val categories = getCategories()
        val tasks =
            Json.decodeFromString<List<Task>>(jsonString).let {
                if (categories.isNullOrEmpty()) {
                    it.map { task -> task.copy(category = null) }
                } else {
                    it
                }
            }
        tasks.forEach { db.taskDao().insertTask(it.toEntity()) }
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
        categoryId = category?.id
    )

private fun TaskWithCategory.toModel() =
    Task(
        id = task.id,
        title = task.title,
        description = task.description,
        isCompleted = task.isCompleted,
        category = category?.toModel()
    )
