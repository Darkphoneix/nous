package com.example.nous.api

import com.example.nous.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface NousApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @GET("levels")
    suspend fun getLevels(): Response<List<Level>>

    @GET("levels/{id}")
    suspend fun getLevel(@Path("id") id: String): Response<Level>

    @POST("levels")
    suspend fun createLevel(@Body level: Level): Response<Level>

    @PUT("levels/{id}")
    suspend fun updateLevel(@Path("id") id: String, @Body level: Level): Response<Level>

    @DELETE("levels/{id}")
    suspend fun deleteLevel(@Path("id") id: String): Response<Unit>
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val user: User
)