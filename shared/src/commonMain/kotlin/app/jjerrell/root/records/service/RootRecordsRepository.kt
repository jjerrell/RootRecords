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
import app.jjerrell.root.records.db.entity.EventEntity
import app.jjerrell.root.records.db.entity.TaskEntity
import app.jjerrell.root.records.db.entity.TaskWithCategoryAndEvents
import app.jjerrell.root.records.service.model.Category
import app.jjerrell.root.records.service.model.Task
import app.jjerrell.root.records.service.model.TaskEvent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class RootRecordsRepository(
    factory: DatabaseFactory,
    private val fileReaderService: FileReaderService
) {
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

    suspend fun populateCategoriesFromFile(path: String) {
        fileReaderService.readFile(path)?.let { populateCategories(it) }
    }

    private suspend fun populateCategories(jsonString: String) {
        val categories = Json.decodeFromString<List<Category>>(jsonString)
        categories.forEach { db.categoryDao().insertCategory(it.toEntity()) }
    }
    // endregion

    // region Task
    suspend fun insertTask(task: Task) {
        val taskAndEvents = task.toEntity()
        db.taskDao().insertTask(taskAndEvents.first)
        taskAndEvents.second?.let { db.taskEventDao().insertEvents(it) }
    }

    suspend fun updateTask(task: Task) {
        val taskAndEvents = task.toEntity()
        db.taskDao().updateTask(taskAndEvents.first)
        taskAndEvents.second?.partition {
            it.id == 0
        } ?.let {
            db.taskEventDao().insertEvents(it.first)
            db.taskEventDao().updateEvents(it.second)
        }
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

    suspend fun populateTasksFromFile(path: String) {
        fileReaderService.readFile(path)?.let { populateTasks(it) }
    }

    private suspend fun populateTasks(jsonString: String) {
        val categories = getCategories()
        val tasks =
            Json.decodeFromString<List<Task>>(jsonString).let {
                if (categories.isNullOrEmpty()) {
                    it.map { task -> task.copy(category = null) }
                } else {
                    it
                }
            }.map {
                it.toEntity().first
            }
        tasks.forEach { db.taskDao().insertTask(it) }
    }
    // endregion
}

private fun Category.toEntity() =
    CategoryEntity(id = id ?: 0, name = name, description = description, color = colorValue)

private fun CategoryEntity.toModel() =
    Category(id = id, name = name, description = description, colorValue = color)

private fun Task.toEntity(): Pair<TaskEntity, List<EventEntity>?> =
    TaskEntity(
        id = id ?: 0,
        title = title,
        description = description,
        isCompleted = isCompleted,
        categoryId = category?.id
    ) to events?.map { it.toEntity(id) }

private fun TaskWithCategoryAndEvents.toModel() =
    Task(
        id = task.id,
        title = task.title,
        description = task.description,
        isCompleted = task.isCompleted,
        category = category?.toModel(),
        events = events.map { it.toModel() }
    )

private fun EventEntity.toModel() =
    TaskEvent(
        id = id,
        name = name,
        timeStampMillis = timestamp
    )

private fun TaskEvent.toEntity(taskId: Int?) = EventEntity(
    id = id ?: 0,
    name = name,
    timestamp = timeStampMillis,
    taskId = taskId
)
