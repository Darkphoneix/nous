package data

// data/UserProgress.kt
data class UserProgress(
    val userId: String = "default_user",
    var dailyStreak: Int = 0,
    var xp: Int = 0
)