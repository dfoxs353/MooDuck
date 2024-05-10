package com.mooduck.domain.models

data class AuthUser(
    val userid: String,
    val userName: String,
    val userPassword: String = "",
    val accessToken: String,
    val refreshToken: String,
)