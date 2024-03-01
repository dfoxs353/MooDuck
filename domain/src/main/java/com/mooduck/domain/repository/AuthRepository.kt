package com.mooduck.domain.repository

import com.mooduck.domain.models.Result
import com.mooduck.domain.models.User

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<User>

    suspend fun signup(email: String, password: String, username: String): Result<User>

    suspend fun refresh(token: String): Result<String>
}