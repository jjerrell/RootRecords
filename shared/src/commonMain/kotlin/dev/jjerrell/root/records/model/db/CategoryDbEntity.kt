package dev.jjerrell.root.records.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDbEntity(
    @PrimaryKey val id: String,
    val name: String,
    val color: Long?
)