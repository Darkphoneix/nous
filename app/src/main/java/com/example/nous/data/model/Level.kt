

package com.example.nous.data.model



data class Level(
    val id: String,
    val title: String,
    val description: String,
    val videoUrl: String,
    val order: Int,
    val createdAt: String,
    val createdBy: String,
    val questions: List<Question>
)