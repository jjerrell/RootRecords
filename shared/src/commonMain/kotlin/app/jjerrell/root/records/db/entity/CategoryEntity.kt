package app.jjerrell.root.records.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") val id: Int = 0,
    val name: String,
    val color: Long
)
