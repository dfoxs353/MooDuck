package com.mooduck.data.remote.user

import com.mooduck.data.remote.books.BooksResponse

data class UserResponse<T>(
    val data: T?,
    val error: Throwable?
)


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
