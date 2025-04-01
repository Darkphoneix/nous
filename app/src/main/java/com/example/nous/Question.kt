package com.example.nous

data class Question(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)