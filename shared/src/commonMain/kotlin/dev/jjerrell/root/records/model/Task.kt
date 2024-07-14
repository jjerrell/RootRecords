package dev.jjerrell.root.records.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Task(
    val id: String,
    val name: String,
    val description: String,
    val timestamp: Instant,
    val category: Category?
) {
    val dateTime: LocalDateTime
        get() = timestamp.toLocalDateTime(
        timeZone = TimeZone.currentSystemDefault()
    )
}

data class Category(
    val id: String,
    val name: String,
    val color: Long?
)