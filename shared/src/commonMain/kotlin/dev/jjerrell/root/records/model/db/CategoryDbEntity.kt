package dev.jjerrell.root.records.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDbEntity(
    @ColumnInfo(name = "category_id")
    @PrimaryKey val id: String,
    val name: String,
    val color: Long?
)