package com.example.mooduck.data.remote.auth

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/registration")
    fun registerUser(@Body request: UserRegistrationRequest): Deferred<Void>

    @POST("api/auth/login")
    fun loginUser(@Body request: UserLoginRequest): Deferred<Void>

    @POST("api/auth/logout")
    fun logoutUser(): Deferred<Void>
}