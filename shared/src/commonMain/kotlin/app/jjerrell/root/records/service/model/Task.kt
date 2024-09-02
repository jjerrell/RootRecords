package app.jjerrell.root.records.service.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val categoryId: Category?
)
