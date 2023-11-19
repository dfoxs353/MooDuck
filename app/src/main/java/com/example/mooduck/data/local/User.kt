package com.example.mooduck.data.local

data class User(
    val userid: String,
    val userPassword: String,
    val accessToken: String,
    val refreshToken: String,
)