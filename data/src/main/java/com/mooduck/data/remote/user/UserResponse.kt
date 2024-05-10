package com.mooduck.data.remote.user

import com.mooduck.data.remote.books.BooksResponse

data class ChangeUserData(
    val id: String,
    val username: String,
    val email: String,
    val password: String
)

data class ChangeResetPasswordData(
    val token: String,
    val password: String
)

data class UserResponse(
    val _id: String,
    val email: String,
    val username: String,
    val roles: List<String>
)