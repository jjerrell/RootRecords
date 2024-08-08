package dev.jjerrell.root.records.model.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CategoryDbEntity::class,
            parentColumns = ["category_id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskDbEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val date: Long,
    @ColumnInfo(name = "category_id", index = true)
    val categoryId: String?
)

data class TaskWithCategory(
    @Embedded val task: TaskDbEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id"
    )
    val category: CategoryDbEntity?
)