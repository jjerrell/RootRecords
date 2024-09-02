package app.jjerrell.root.records.service.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int? = null,
    val name: String,
    val description: String?,
    val colorValue: Int?
)
