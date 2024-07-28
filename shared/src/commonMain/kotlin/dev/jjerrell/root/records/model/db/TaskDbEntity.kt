package dev.jjerrell.root.records.model.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity
data class TaskDbEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val date: Long,
    @Embedded("cat_") val category: CategoryDbEntity?
)