package com.example.mooduck.data.remote.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)

data class User(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
    val isActivated: Boolean,
    val activationLink: String
)