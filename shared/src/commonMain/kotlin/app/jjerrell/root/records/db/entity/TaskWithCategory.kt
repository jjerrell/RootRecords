package app.jjerrell.root.records.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithCategory(
    @Embedded val task: TaskEntity,
    @Relation(parentColumn = "category_id", entityColumn = "category_id")
    val category: CategoryEntity?
)
