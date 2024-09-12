package app.jjerrell.root.records.service.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskEvent(
    val id: Int? = null,
    val name: String,
    val timestampSeconds: Long,
)