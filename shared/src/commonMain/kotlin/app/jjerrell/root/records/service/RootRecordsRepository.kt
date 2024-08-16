package app.jjerrell.root.records.service

import app.jjerrell.root.records.service.model.Category
import app.jjerrell.root.records.db.DatabaseFactory
import app.jjerrell.root.records.db.createDatabase
import app.jjerrell.root.records.db.entity.CategoryEntity
import kotlinx.coroutines.flow.firstOrNull

class RootRecordsRepository(
    factory: DatabaseFactory,
) {
    private val db = createDatabase(factory)

    suspend fun insertCategory(category: Category) {
        db.categoryDao()
            .insertCategory(category.toEntity())
    }

    suspend fun updateCategory(category: Category) {
        db.categoryDao()
            .updateCategory(category.toEntity())
    }

    suspend fun getCategories(): List<Category>? {
        return db.categoryDao()
            .getAllCategories()
            .firstOrNull()
            ?.map { it.toModel() }
    }

    suspend fun getCategoryById(id: Int): Category? {
        return db.categoryDao()
            .getCategoryById(id)
            .firstOrNull()
            ?.toModel()
    }
}

private fun Category.toEntity() = CategoryEntity(
    name = name,
    color = color
)

private fun CategoryEntity.toModel() = Category(
    id = id,
    name = name,
    color = color
)