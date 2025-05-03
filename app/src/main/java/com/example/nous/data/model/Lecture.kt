package com.example.nous.data.model

data class Lecture(
    val id: Int,
    val name: String,
    val description: String,
    val videoPath: String? = null, // Path to the associated video
    val questions: MutableList<Question> = mutableListOf() // List of questions for the lecture
)