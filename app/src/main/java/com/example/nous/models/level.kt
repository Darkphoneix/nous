package com.example.nous.models



data class Level(
    val id: Int,
    val name: String,
    val videoPath: String,
    val questions: List<Question>
)