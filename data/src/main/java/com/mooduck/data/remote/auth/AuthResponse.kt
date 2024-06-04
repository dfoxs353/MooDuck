package com.mooduck.data.remote.auth



data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: AuthUserResponse
)

data class AuthUserResponse(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
    val isActivated: Boolean,
    val activationLink: String
)