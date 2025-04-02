package com.example.nous.data.model


data class Question(
    val id: String,
    val levelId: String,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)