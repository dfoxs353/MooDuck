package com.example.mooduck.data.remote.auth

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/registration")
    fun registerUser(@Body request: UserRegistrationRequest): Deferred<AuthResponse>

    @POST("auth/login")
    fun loginUser(@Body request: UserLoginRequest): Deferred<AuthResponse>

    @POST("auth/logout")
    fun logoutUser(): Deferred<Void>

    @Headers("Content-Type: application/json")
    @GET("auth/refresh")
    fun refreshTokens(): Deferred<AuthResponse>
}