package com.example.nous.data.model

data class User(
    val id: String,
    val username: String,
    val role: UserRole,
    val createdAt: String
)

enum class UserRole {
    USER,
    ADMIN
}