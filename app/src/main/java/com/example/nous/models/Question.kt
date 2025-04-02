package com.example.nous.models

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val levelId: Int
)