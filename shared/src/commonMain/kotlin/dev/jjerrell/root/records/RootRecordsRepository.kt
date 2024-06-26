package dev.jjerrell.root.records

import dev.jjerrell.root.records.db.CategoryEntity
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.createDatabase

class RootRecordsRepository(databaseDriverFactory: DriverFactory) {
    private val database = createDatabase(databaseDriverFactory)
    private val categoryQueries = database.categoryEntityQueries

    fun getCategories(): List<CategoryEntity> {
        return categoryQueries.selectAll().executeAsList()
    }
}