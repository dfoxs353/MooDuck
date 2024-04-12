package com.mooduck.domain.models

data class User(
    val userid: String,
    val userName: String,
    val userPassword: String = "",
    val accessToken: String,
    val refreshToken: String,
)