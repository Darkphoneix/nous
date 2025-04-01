package data

// data/PhilosophyTopic.kt
data class PhilosophyTopic(
    val id: Int = 0,
    val title: String,
    val philosopher: String,
    val difficulty: String,
    var isCompleted: Boolean = false
)