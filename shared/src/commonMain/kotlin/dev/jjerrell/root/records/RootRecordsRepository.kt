package dev.jjerrell.root.records

import dev.jjerrell.root.records.db.Category
import dev.jjerrell.root.records.db.DriverFactory
import dev.jjerrell.root.records.db.createDatabase

class RootRecordsRepository(databaseDriverFactory: DriverFactory) {
    private val database = createDatabase(databaseDriverFactory)
    private val categoryQueries = database.categoryQueries

    fun getCategories(): List<Category> {
        return categoryQueries.selectAll().executeAsList()
    }
}