package com.mooduck.domain.repository

import com.mooduck.domain.models.Result
import com.mooduck.domain.models.AuthUser

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<AuthUser>

    suspend fun signup(email: String, password: String, username: String): Result<AuthUser>

    suspend fun refresh(token: String): Result<String>
}