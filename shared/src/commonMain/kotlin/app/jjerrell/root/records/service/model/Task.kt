package app.jjerrell.root.records.service.model

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val categoryId: Category?
)
