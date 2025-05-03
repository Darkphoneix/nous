package com.example.nous.repository

import com.example.nous.api.AuthResponse
import com.example.nous.api.LoginRequest
import com.example.nous.api.NousApi
import com.example.nous.data.model.*
import javax.inject.Inject

class NousRepository @Inject constructor(
    private val api: NousApi
) {
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLevels(): Result<List<Level>> {
        return try {
            val response = api.getLevels()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch levels"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createLevel(level: Level): Result<Level> {
        return try {
            val response = api.createLevel(level)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to create level"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}